package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PhotoRepositoryImpl(
    private val service: PhotoService
) : PhotoRepository {

    override suspend fun getImages(idResidence: String): Resource<List<Photo>> =
        when (val response = ResponseToRequest.call { service.getImages(idResidence) }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta de imágenes no contiene datos")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun uploadImage(
        residenceId: String,
        file: File,
        photo: Photo
    ): Resource<Unit> {
        val textType = "text/plain".toMediaTypeOrNull()
        val image = MultipartBody.Part.createFormData(
            "image",
            "image.jpg",
            file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        return when (val response = ResponseToRequest.call {
            service.uploadImage(
                image = image,
                residenceId = residenceId.toRequestBody(textType),
                title = (photo.title ?: "").toRequestBody(textType),
                description = (photo.description ?: "").toRequestBody(textType),
                principal = (photo.principal ?: 0).toString().toRequestBody(textType)
            )
        }) {
            is Resource.Success -> if (response.data.data != null) {
                Resource.Success(Unit)
            } else {
                Resource.Failure("La respuesta de carga no contiene la ruta de la imagen")
            }
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun updateImage(residenceId: String, photo: Photo): Resource<Photo> =
        when (val response = ResponseToRequest.call {
            service.updateImage(residenceId, photo.id, photo.title, photo.description, photo.principal)
        }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta de actualización no contiene la imagen")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun deleteImage(residenceId: String, photo: Photo): Resource<Unit> =
        when (val response = ResponseToRequest.call { service.deleteImage(residenceId, photo.id) }) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }
}
