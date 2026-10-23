package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.Photo
import com.altoservicios.centros.domain.util.Resource
import java.io.File

interface PhotoRepository {

    suspend fun getImages(): Resource<List<Photo>>

    suspend fun uploadImage(file: File, photo: Photo): Resource<Unit>

    suspend fun updateImage(photo: Photo): Resource<Photo>

    suspend fun deleteImage(photo: Photo): Resource<Photo>
}
