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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientResidenceViewModel @Inject constructor(
    private val residencesUseCase: ResidencesUseCase,
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
                residencesUseCase.getResidenceById(residenceId)
            } else {
                Resource.Failure("No residence ID provided")
            }
            
            val provincesResult = residencesUseCase.getProvinces()
            val roomsResult = if (residenceId.isNotBlank()) {
                Log.d("ClientResidenceViewModel", "Fetching rooms by ID: $residenceId")
                residencesUseCase.getMyRooms(residenceId)
            } else {
                Log.w("ClientResidenceViewModel", "rooms ID is empty or blank!")
                Resource.Failure("No residence ID provided")
            }

            if (residenceResult is Resource.Success) {
                residence = residenceResult.data
                Log.d("ClientResidenceViewModel", "Residence loaded successfully: ${residence.name}")
                residence.provinceId?.toIntOrNull()?.takeIf { it > 0 }?.let { loadTowns(it) }
            }
            if (provincesResult is Resource.Success) provinces = provincesResult.data
            if (roomsResult is Resource.Success) {
                rooms = roomsResult.data
                availability = rooms.associate { it.id to (it.numAvailable ?: 0).toString() }
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
    }

    fun onProvinceSelected(idProvince: Int?) {
        residence = residence.copy(provinceId = idProvince?.toString(), townId = null)
        towns = emptyList()
        idProvince?.let { loadTowns(it) }
    }

    fun onTownSelected(idTown: Int?) {
        residence = residence.copy(townId = idTown?.toString())
    }

    fun save(successMessage: String) {
        if (residenceId.isBlank()) {
            message = "No residence ID provided"
            return
        }
        isLoading = true
        viewModelScope.launch {
            val result = residencesUseCase.updateMyResidence(residence)
            when (result) {
                is Resource.Success -> {
                    residence = result.data
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
    }

    fun saveRooms(successMessage: String) {
        val rooms = availability.map { (id, value) -> RoomAvailability(id, value.toIntOrNull() ?: 0) }
        if (rooms.isEmpty()) return
        if (residenceId.isBlank()) {
            message = "No residence ID provided"
            return
        }

        isLoading = true
        viewModelScope.launch {
            when (val result = residencesUseCase.updateMyRooms(residenceId, rooms)) {
                is Resource.Success -> {
                    this@ClientResidenceViewModel.rooms = this@ClientResidenceViewModel.rooms.map { room ->
                        room.copy(numAvailable = availability[room.id]?.toIntOrNull() ?: 0)
                    }
                    availability = this@ClientResidenceViewModel.rooms.associate { it.id to (it.numAvailable ?: 0).toString() }
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
            val result = residencesUseCase.getTowns(idProvince)
            if (result is Resource.Success) towns = result.data
            if (result is Resource.Failure) message = result.message
        }
    }

}
