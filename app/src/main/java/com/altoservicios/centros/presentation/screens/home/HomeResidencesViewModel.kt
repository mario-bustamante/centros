package com.altoservicios.centros.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Dependence
import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.model.Sector
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.useCase.residences.ResidencesUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Compartido por el listado, el buscador y el mapa de residencias */
@HiltViewModel
class HomeResidencesViewModel @Inject constructor(
    private val residencesUseCase: ResidencesUseCase
) : ViewModel() {

    var search by mutableStateOf(Search())
        private set

    var errorMessage by mutableStateOf("")

    // Listado
    var residences by mutableStateOf<List<Residence>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    private var nextPage: Int? = 1
    private var listJob: Job? = null

    // Mapa
    var mapResidences by mutableStateOf<List<Residence>>(emptyList())
        private set
    var isLoadingMap by mutableStateOf(false)
        private set

    // Opciones del buscador
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
    var prices by mutableStateOf<List<Price>>(emptyList())
        private set
    private var optionsLoaded = false

    init {
        loadNextPage()
    }

    fun applySearch(newSearch: Search) {
        search = newSearch
        refresh()
    }

    fun refresh() {
        listJob?.cancel()
        isLoading = false
        residences = emptyList()
        nextPage = 1
        loadNextPage()
    }

    fun loadNextPage() {
        val page = nextPage ?: return
        if (isLoading) return

        isLoading = true
        listJob = viewModelScope.launch {
            when (val result = residencesUseCase.getResidences(page, search)) {
                is Resource.Success -> {
                    val data = result.data
                    residences = if (page == 1) data.residences else residences + data.residences
                    nextPage = if (data.residences.isEmpty()) null else data.nextPage
                }
                is Resource.Failure -> errorMessage = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }

    fun loadMap(latitude: Double?, longitude: Double?) {
        // Solo se filtra por cercania cuando el usuario lo pidio y se conoce su ubicacion
        val mapSearch = if (search.near && latitude != null && longitude != null) {
            search.copy(latitude = latitude, longitude = longitude)
        } else {
            search.copy(latitude = null, longitude = null)
        }

        isLoadingMap = true
        viewModelScope.launch {
            when (val result = residencesUseCase.getResidencesMap(1, mapSearch)) {
                is Resource.Success -> mapResidences = result.data.residences
                is Resource.Failure -> errorMessage = result.message
                Resource.Loading -> Unit
            }
            isLoadingMap = false
        }
    }

    fun loadSearchOptions() {
        if (optionsLoaded) return
        optionsLoaded = true
        viewModelScope.launch {
            val provincesResult = residencesUseCase.getProvinces()
            val roomsResult = residencesUseCase.getRooms()
            val sectorsResult = residencesUseCase.getSectors()
            val dependenciesResult = residencesUseCase.getDependencies()
            val pricesResult = residencesUseCase.getPrices()

            if (provincesResult is Resource.Success) provinces = provincesResult.data
            if (roomsResult is Resource.Success) rooms = roomsResult.data
            if (sectorsResult is Resource.Success) sectors = sectorsResult.data
            if (dependenciesResult is Resource.Success) dependencies = dependenciesResult.data
            if (pricesResult is Resource.Success) prices = pricesResult.data

            val failure = listOf(provincesResult, roomsResult, sectorsResult, dependenciesResult, pricesResult)
                .filterIsInstance<Resource.Failure<*>>()
                .firstOrNull()
            if (failure != null) {
                optionsLoaded = false
                errorMessage = failure.message
            }
        }
    }

    fun loadTowns(idProvince: Int?) {
        towns = emptyList()
        idProvince ?: return
        viewModelScope.launch {
            val result = residencesUseCase.getTowns(idProvince)
            if (result is Resource.Success) towns = result.data
            if (result is Resource.Failure) errorMessage = result.message
        }
    }
}
