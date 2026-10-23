package com.altoservicios.centros.presentation.screens.admin.photo

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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AdminPhotoViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Fotografia existente (null cuando se crea una nueva)
    val photo: Photo? = savedStateHandle.get<String>(PHOTO_ARG)?.let { Photo.fromJson(it) }

    var title by mutableStateOf(photo?.title.orEmpty())
    var description by mutableStateOf(photo?.description.orEmpty())
    var principal by mutableStateOf(photo?.principal == 1)

    // Archivo donde la camara guarda la foto y archivo ya capturado
    var pendingFile: File? = null
    var imageFile by mutableStateOf<File?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
    var finished by mutableStateOf(false)
        private set

    fun onImageCaptured(file: File) {
        imageFile = file
    }

    fun save() {
        val newPhoto = Photo(
            id = photo?.id,
            title = title,
            description = description,
            principal = if (principal) 1 else 0
        )
        val file = imageFile

        if (file == null && photo == null) {
            message = "Toma una fotografía primero"
            return
        }

        execute {
            if (file != null) {
                photoUseCase.uploadPhoto(file, newPhoto)
            } else {
                photoUseCase.updatePhoto(newPhoto)
            }
        }
    }

    fun delete() {
        val current = photo ?: return
        execute { photoUseCase.deletePhoto(current) }
    }

    private fun execute(block: suspend () -> Resource<*>) {
        isLoading = true
        viewModelScope.launch {
            when (val result = block()) {
                is Resource.Success -> finished = true
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }

    companion object {
        const val PHOTO_ARG = "photo"
    }
}
