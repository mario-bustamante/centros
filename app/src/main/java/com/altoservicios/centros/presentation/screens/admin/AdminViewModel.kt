package com.altoservicios.centros.presentation.screens.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.useCase.residences.ResidencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val residencesUseCase: ResidencesUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")

    init {
        load()
    }

    fun load() {
        isLoading = true
        viewModelScope.launch {

            isLoading = false
        }
    }
}