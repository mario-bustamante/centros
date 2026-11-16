package com.altoservicios.centros.presentation.screens.client.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.useCase.photo.PhotoUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientPhotosViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val residenceId: String = savedStateHandle.get<String>("residenceId") ?: ""

    var photos by mutableStateOf<List<Photo>>(emptyList())
        private set

    var imageCount by mutableStateOf<Int?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")

    fun load() {
        if (residenceId.isBlank()) {
            message = "No residence ID provided"
            return
        }
        isLoading = true
        viewModelScope.launch {
            when (val result = photoUseCase.getPhotos(residenceId)) {
                is Resource.Success -> {
                    val receivedPhotos = result.data.data
                    if (receivedPhotos != null) {
                        photos = receivedPhotos
                        imageCount = result.data.meta?.imageCount
                    } else {
                        message = "La respuesta de imágenes no contiene datos"
                    }
                }
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }
}
