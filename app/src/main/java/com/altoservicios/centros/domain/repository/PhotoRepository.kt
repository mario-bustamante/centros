package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.model.PhotoResponse
import com.altoservicios.centros.domain.util.Resource
import java.io.File

interface PhotoRepository {

    suspend fun getImages(idResidence: String): Resource<PhotoResponse>

    suspend fun storeImage(residenceId: String, file: File, photo: Photo): Resource<Unit>

    suspend fun updateImage(residenceId: String, photo: Photo): Resource<Photo>

    suspend fun deleteImage(residenceId: String, photo: Photo): Resource<Unit>
}
