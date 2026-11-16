package com.altoservicios.centros.presentation.screens.client.photo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.useCase.photo.PhotoUseCase
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.presentation.screens.client.ClientResidenceCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ClientPhotoViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    private val cache: ClientResidenceCache,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Fotografia existente (null cuando se crea una nueva)
    val photo: Photo? = savedStateHandle.get<String>(PHOTO_ARG)?.let { Photo.fromJson(it) }
    private val residenceId: String = savedStateHandle.get<String>("residenceId") ?: ""

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

    fun update() {

        val currentPhoto = photo
        if (residenceId.isBlank()) {
            message = "No se proporcionó el ID de la residencia"
            return
        }
        if (currentPhoto?.id == null) {
            message = "No se proporcionó el ID de la imagen"
            return
        }
        if (imageFile != null) {
            message = "No se puede reemplazar la fotografía existente"
            return
        }

        val updatedPhoto = currentPhoto.copy(
            title = title,
            description = description,
            principal = if (principal) 1 else 0
        )
        Log.d("ClientPhotoViewModel", "update")
        Log.d("ClientPhotoViewModel", updatedPhoto.toJson())


        execute { photoUseCase.updatePhoto(residenceId, updatedPhoto) }
    }

    fun store() {

        val newPhoto = Photo(
            title = title,
            description = description,
            principal = if (principal) 1 else 0
        )
        val file = imageFile

        if (residenceId.isBlank()) {
            message = "No se proporcionó el ID de la residencia"
            return
        }
        if (imageFile == null || file == null) {
            message = "Debe subir una fotografía"
            return
        }
        if (newPhoto.title.isNullOrBlank()) {
            message = "Debe agregar el titulo"
            return
        }

        Log.d("ClientPhotoViewModel", "store")
        Log.d("ClientPhotoViewModel", newPhoto.toJson())
        execute { photoUseCase.storePhoto(residenceId, file, newPhoto) }
    }

    fun delete() {
        val current = photo ?: return
        if (residenceId.isBlank()) {
            message = "No se proporcionó el ID de la residencia"
            return
        }
        execute { photoUseCase.deletePhoto(residenceId, current) }
    }

    private fun execute(block: suspend () -> Resource<*>) {
        isLoading = true
        viewModelScope.launch {
            cache.invalidatePhotos(residenceId)
            cache.invalidateResidence(residenceId)
            val result = try {
                block()
            } finally {
                cache.invalidatePhotos(residenceId)
                cache.invalidateResidence(residenceId)
            }
            when (result) {
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
