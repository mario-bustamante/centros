package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import com.altoservicios.centros.domain.util.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PhotoRepositoryImpl(
    private val service: PhotoService
) : PhotoRepository {

    override suspend fun getImages(): Resource<List<Photo>> =
        ResponseToRequest.call { service.getImages() }.map { it.data.orEmpty() }

    override suspend fun uploadImage(file: File, photo: Photo): Resource<Unit> {
        val textType = "text/plain".toMediaTypeOrNull()
        val image = MultipartBody.Part.createFormData(
            "image",
            "image.jpg",
            file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
        return ResponseToRequest.call {
            service.uploadImage(
                image = image,
                title = (photo.title ?: "").toRequestBody(textType),
                description = (photo.description ?: "").toRequestBody(textType),
                principal = (photo.principal ?: 0).toString().toRequestBody(textType)
            )
        }
    }

    override suspend fun updateImage(photo: Photo): Resource<Photo> =
        ResponseToRequest.call {
            service.updateImage(photo.id, photo.title, photo.description, photo.principal)
        }

    override suspend fun deleteImage(photo: Photo): Resource<Photo> =
        ResponseToRequest.call { service.deleteImage(photo.id) }
}
