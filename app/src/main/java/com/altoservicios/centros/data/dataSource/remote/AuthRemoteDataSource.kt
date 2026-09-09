package com.altoservicios.centros.data.dataSource.remote

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.User
import retrofit2.Response

interface AuthRemoteDataSource {

    suspend fun login(email: String, password: String): Response<AuthResponse>

    suspend fun register(user: User): Response<AuthResponse>
}