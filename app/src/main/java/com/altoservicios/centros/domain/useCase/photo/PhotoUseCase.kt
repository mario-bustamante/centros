package com.altoservicios.centros.domain.useCase.photo

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.repository.PhotoRepository
import java.io.File

data class PhotoUseCase(
    val getPhotos: GetPhotosUseCase,
    val uploadPhoto: UploadPhotoUseCase,
    val updatePhoto: UpdatePhotoUseCase,
    val deletePhoto: DeletePhotoUseCase,
)

class GetPhotosUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke() = repository.getImages()
}

class UploadPhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(file: File, photo: Photo) = repository.uploadImage(file, photo)
}

class UpdatePhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(photo: Photo) = repository.updateImage(photo)
}

class DeletePhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(photo: Photo) = repository.deleteImage(photo)
}
