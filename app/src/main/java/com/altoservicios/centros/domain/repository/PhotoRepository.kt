package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.util.Resource
import java.io.File

interface PhotoRepository {

    suspend fun getImages(idResidence: String): Resource<List<Photo>>

    suspend fun uploadImage(residenceId: String, file: File, photo: Photo): Resource<Unit>

    suspend fun updateImage(residenceId: String, photo: Photo): Resource<Photo>

    suspend fun deleteImage(residenceId: String, photo: Photo): Resource<Unit>
}
