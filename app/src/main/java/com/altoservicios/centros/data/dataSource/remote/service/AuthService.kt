package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.LoginRequest
import com.altoservicios.centros.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body() user: User,
    ): Response<AuthResponse>
}