package com.altoservicios.centros.presentation.screens.admin.residence

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Dependence
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.Sector
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.useCase.residences.ResidencesUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminResidenceViewModel @Inject constructor(
    private val residencesUseCase: ResidencesUseCase
) : ViewModel() {

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
    var sectors by mutableStateOf<List<Sector>>(emptyList())
        private set
    var dependencies by mutableStateOf<List<Dependence>>(emptyList())
        private set

    var selectedRooms by mutableStateOf<Set<Int>>(emptySet())
        private set
    var selectedSectors by mutableStateOf<Set<Int>>(emptySet())
        private set
    var selectedDependencies by mutableStateOf<Set<Int>>(emptySet())
        private set

    init {
        load()
    }

    fun load() {
        isLoading = true
        viewModelScope.launch {
            val residenceResult = residencesUseCase.getMyResidence()
            val provincesResult = residencesUseCase.getProvinces()
            val roomsResult = residencesUseCase.getMyRooms()
            val sectorsResult = residencesUseCase.getMySectors()
            val dependenciesResult = residencesUseCase.getMyDependencies()

            if (residenceResult is Resource.Success) {
                residence = residenceResult.data
                residence.provinceId?.toIntOrNull()?.takeIf { it > 0 }?.let { loadTowns(it) }
            }
            if (provincesResult is Resource.Success) provinces = provincesResult.data
            if (roomsResult is Resource.Success) {
                rooms = roomsResult.data
                selectedRooms = rooms.filter { it.residenceId != null }.map { it.id }.toSet()
            }
            if (sectorsResult is Resource.Success) {
                sectors = sectorsResult.data
                selectedSectors = sectors.filter { it.residenceId != null }.map { it.id }.toSet()
            }
            if (dependenciesResult is Resource.Success) {
                dependencies = dependenciesResult.data
                selectedDependencies = dependencies.filter { it.residenceId != null }.map { it.id }.toSet()
            }

            listOf(residenceResult, provincesResult, roomsResult, sectorsResult, dependenciesResult)
                .filterIsInstance<Resource.Failure<*>>()
                .firstOrNull()
                ?.let { message = it.message }
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

    fun toggleRoom(id: Int) {
        selectedRooms = selectedRooms.toggle(id)
    }

    fun toggleSector(id: Int) {
        selectedSectors = selectedSectors.toggle(id)
    }

    fun toggleDependence(id: Int) {
        selectedDependencies = selectedDependencies.toggle(id)
    }

    fun save(successMessage: String) {
        isLoading = true
        viewModelScope.launch {
            val result = residencesUseCase.updateMyResidence(
                residence = residence,
                dependence = selectedDependencies.toIdString(),
                sector = selectedSectors.toIdString(),
                room = selectedRooms.toIdString()
            )
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

    private fun loadTowns(idProvince: Int) {
        viewModelScope.launch {
            val result = residencesUseCase.getTowns(idProvince)
            if (result is Resource.Success) towns = result.data
            if (result is Resource.Failure) message = result.message
        }
    }

    private fun Set<Int>.toggle(id: Int): Set<Int> = if (id in this) this - id else this + id

    // El servidor espera los ids separados por "_" (ej: "1_4_")
    private fun Set<Int>.toIdString(): String = joinToString(separator = "") { "${it}_" }
}
