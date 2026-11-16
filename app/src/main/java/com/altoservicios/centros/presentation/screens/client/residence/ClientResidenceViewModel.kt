package com.altoservicios.centros.presentation.screens.client.residence

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.RoomAvailability
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.useCase.residences.ResidencesUseCase
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.presentation.screens.client.ClientResidenceCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientResidenceViewModel @Inject constructor(
    private val residencesUseCase: ResidencesUseCase,
    private val cache: ClientResidenceCache,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val residenceId: String = savedStateHandle.get<String>("residenceId") ?: ""

    var residence by mutableStateOf(Residence())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")

    var provinces by mutableStateOf<List<Province>>(emptyList())
        private set
    var towns by mutableStateOf<List<Town>>(emptyList())
        private set
    var rooms by mutableStateOf<List<Room>>(emptyList())
        private set
    var availability by mutableStateOf<Map<Int, String>>(emptyMap())
        private set

    init {
        Log.d("ClientResidenceViewModel", "Init - SavedStateHandle keys: ${savedStateHandle.keys()}")
        Log.d("ClientResidenceViewModel", "Init - Residence ID from SavedStateHandle: '$residenceId'")
        load()
    }

    fun load() {
        isLoading = true
        viewModelScope.launch {
            Log.d("ClientResidenceViewModel", "Loading residence with ID: '$residenceId'")

            val residenceResult = if (residenceId.isNotBlank()) {
                cache.residence(residenceId) { residencesUseCase.getResidenceById(residenceId) }
            } else {
                Resource.Failure("No residence ID provided")
            }
            
            val provincesResult = cache.provinces(residenceId) { residencesUseCase.getProvinces() }
            val roomsResult = if (residenceId.isNotBlank()) {
                Log.d("ClientResidenceViewModel", "Fetching rooms by ID: $residenceId")
                cache.rooms(residenceId) { residencesUseCase.getMyRooms(residenceId) }
            } else {
                Log.w("ClientResidenceViewModel", "rooms ID is empty or blank!")
                Resource.Failure("No residence ID provided")
            }

            if (residenceResult is Resource.Success) {
                residence = cache.residenceDraft(residenceId) ?: residenceResult.data
                Log.d("ClientResidenceViewModel", "Residence loaded successfully: ${residence.name}")
                residence.provinceId?.toIntOrNull()?.takeIf { it > 0 }?.let { loadTowns(it) }
            }
            if (provincesResult is Resource.Success) provinces = provincesResult.data
            if (roomsResult is Resource.Success) {
                rooms = roomsResult.data
                availability = cache.roomsDraft(residenceId)
                    ?: rooms.associate { it.id to (it.numAvailable ?: 0).toString() }
            }

            listOf(residenceResult, provincesResult, roomsResult)
                .filterIsInstance<Resource.Failure<*>>()
                .firstOrNull()
                ?.let { 
                    message = it.message
                    Log.e("ClientResidenceViewModel", "Error: ${it.message}")
                }
            isLoading = false
        }
    }

    fun onResidenceChange(newResidence: Residence) {
        residence = newResidence
        cache.setResidenceDraft(residenceId, newResidence)
    }

    fun onProvinceSelected(idProvince: Int?) {
        residence = residence.copy(provinceId = idProvince?.toString(), townId = null)
        cache.setResidenceDraft(residenceId, residence)
        towns = emptyList()
        idProvince?.let { loadTowns(it) }
    }

    fun onTownSelected(idTown: Int?) {
        residence = residence.copy(townId = idTown?.toString())
        cache.setResidenceDraft(residenceId, residence)
    }

    fun save(successMessage: String) {
        if (residenceId.isBlank()) {
            message = "No residence ID provided"
            return
        }
        val savedResidence = residence
        isLoading = true
        viewModelScope.launch {
            cache.invalidateResidence(residenceId)
            val result = try {
                residencesUseCase.updateMyResidence(savedResidence)
            } finally {
                cache.invalidateResidence(residenceId)
            }
            when (result) {
                is Resource.Success -> {
                    cache.clearResidenceDraft(residenceId, savedResidence)
                    residence = cache.residenceDraft(residenceId) ?: result.data
                    message = successMessage
                }
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }

    fun onAvailabilityChange(id: Int, value: String) {
        availability = availability + (id to value.filter { it.isDigit() }.take(5))
        cache.setRoomsDraft(residenceId, availability)
    }

    fun saveRooms(successMessage: String) {
        val savedAvailability = availability
        val rooms = savedAvailability.map { (id, value) -> RoomAvailability(id, value.toIntOrNull() ?: 0) }
        if (rooms.isEmpty()) return
        if (residenceId.isBlank()) {
            message = "No residence ID provided"
            return
        }

        isLoading = true
        viewModelScope.launch {
            cache.invalidateRooms(residenceId)
            val result = try {
                residencesUseCase.updateMyRooms(residenceId, rooms)
            } finally {
                cache.invalidateRooms(residenceId)
            }
            when (result) {
                is Resource.Success -> {
                    cache.clearRoomsDraft(residenceId, savedAvailability)
                    this@ClientResidenceViewModel.rooms = this@ClientResidenceViewModel.rooms.map { room ->
                        room.copy(numAvailable = savedAvailability[room.id]?.toIntOrNull() ?: 0)
                    }
                    availability = cache.roomsDraft(residenceId)
                        ?: this@ClientResidenceViewModel.rooms.associate { it.id to (it.numAvailable ?: 0).toString() }
                    message = successMessage
                }
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }

    private fun loadTowns(idProvince: Int) {
        viewModelScope.launch {
            val result = cache.towns(residenceId, idProvince) { residencesUseCase.getTowns(idProvince) }
            if (result is Resource.Success) towns = result.data
            if (result is Resource.Failure) message = result.message
        }
    }

}
