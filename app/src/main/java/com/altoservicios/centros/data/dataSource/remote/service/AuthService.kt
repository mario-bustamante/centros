package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}