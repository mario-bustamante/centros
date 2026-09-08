package com.altoservicios.centros.data.dataSource.remote

import com.altoservicios.centros.domain.model.AuthResponse
import retrofit2.Response

interface AuthRemoteDataSource {

    suspend fun login(email: String, password: String): Response<AuthResponse>
}