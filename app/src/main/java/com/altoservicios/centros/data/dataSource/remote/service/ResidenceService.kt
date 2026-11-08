package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.MyResidencesResponse
import com.altoservicios.centros.domain.model.ResidenceResponse
import com.altoservicios.centros.domain.model.RoomResponse
import com.altoservicios.centros.domain.model.UpdateRoomsRequest
import com.altoservicios.centros.domain.model.UpdateRoomsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ResidenceService {

    @GET("user/residences")
    suspend fun getMyResidences(): Response<MyResidencesResponse>

    @GET("user/residence/{residence_id}")
    suspend fun getResidenceById(
        @Path("residence_id") residenceId: String,
    ): Response<ResidenceResponse>

    @PUT("user/residence")
    suspend fun updateMyResidence(@QueryMap params: Map<String, String>): Response<ResidenceResponse>

    @GET("user/residence/rooms/{residence_id}")
    suspend fun getMyResidenceRooms(
        @Path("residence_id") residenceId: String,
    ): Response<RoomResponse>

    @PUT("user/residence/rooms")
    suspend fun updateMyResidenceRooms(@Body request: UpdateRoomsRequest): Response<UpdateRoomsResponse>
}
