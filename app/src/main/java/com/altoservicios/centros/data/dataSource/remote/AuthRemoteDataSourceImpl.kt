package com.altoservicios.centros.data.dataSource.remote

import com.altoservicios.centros.data.dataSource.remote.service.AuthService
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.LoginRequest
import retrofit2.Response

class AuthRemoteDataSourceImpl(private val authService: AuthService): AuthRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse> = authService.login(LoginRequest(email, password))
}