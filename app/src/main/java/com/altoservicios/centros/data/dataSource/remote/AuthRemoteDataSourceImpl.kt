package com.altoservicios.centros.data.dataSource.remote

import com.altoservicios.centros.data.dataSource.remote.service.AuthService
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.LoginRequest
import com.altoservicios.centros.domain.model.User
import retrofit2.Response

class AuthRemoteDataSourceImpl(private val authService: AuthService): AuthRemoteDataSource {

    override suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse> = authService.login(email, password)

    override suspend fun register(
        user: User
    ): Response<AuthResponse> = authService.register(user = user)
}