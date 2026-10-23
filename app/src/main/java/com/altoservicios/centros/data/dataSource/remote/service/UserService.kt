package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {

    @GET("user")
    suspend fun getUser(): Response<UserResponse>

    @PUT("user")
    suspend fun updateUser(
        @Query("username") userName: String?,
        @Query("name") name: String?,
        @Query("address") address: String?,
        @Query("phone") phone: String?
    ): Response<UserResponse>
}
