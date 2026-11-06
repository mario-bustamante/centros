package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.ResidenceResponse
import com.altoservicios.centros.domain.model.RoomResponse
import com.altoservicios.centros.domain.model.UpdateRoomsRequest
import com.altoservicios.centros.domain.model.UpdateRoomsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.QueryMap

interface ResidenceService {

    @GET("residence")
    suspend fun getMyResidence(): Response<ResidenceResponse>

    @PUT("residence")
    suspend fun updateMyResidence(@QueryMap params: Map<String, String>): Response<ResidenceResponse>

    @GET("residence/rooms")
    suspend fun getMyResidenceRooms(): Response<RoomResponse>

    @PUT("residence/rooms")
    suspend fun updateMyResidenceRooms(@Body request: UpdateRoomsRequest): Response<UpdateRoomsResponse>
}
