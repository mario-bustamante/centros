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
    suspend operator fun invoke(idResidence: String) = repository.getImages(idResidence)
}

class UploadPhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(residenceId: String, file: File, photo: Photo) =
        repository.uploadImage(residenceId, file, photo)
}

class UpdatePhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(residenceId: String, photo: Photo) =
        repository.updateImage(residenceId, photo)
}

class DeletePhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(residenceId: String, photo: Photo) =
        repository.deleteImage(residenceId, photo)
}
