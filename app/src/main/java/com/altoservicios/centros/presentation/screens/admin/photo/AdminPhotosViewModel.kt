package com.altoservicios.centros.presentation.screens.admin.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.useCase.photo.PhotoUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPhotosViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    var photos by mutableStateOf<List<Photo>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")

    fun load() {
        isLoading = true
        viewModelScope.launch {
            when (val result = photoUseCase.getPhotos()) {
                is Resource.Success -> photos = result.data
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }
}
