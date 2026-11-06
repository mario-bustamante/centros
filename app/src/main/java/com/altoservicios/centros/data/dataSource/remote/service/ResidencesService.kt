package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.PriceResponse
import com.altoservicios.centros.domain.model.ProvinceResponse
import com.altoservicios.centros.domain.model.ResidencesResponse
import com.altoservicios.centros.domain.model.RoomResponse
import com.altoservicios.centros.domain.model.TownResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ResidencesService {

    /*********** RESIDENCIAS ************/
    @GET("residences")
    suspend fun getResidences(
        @Query("page") page: Int?,
        @QueryMap filters: Map<String, String>
    ): Response<ResidencesResponse>

    @GET("residences/map")
    suspend fun getResidencesMap(
        @Query("page") page: Int?,
        @QueryMap filters: Map<String, String>
    ): Response<ResidencesResponse>

    /*********** BUSCADOR ************/
    @GET("residences/provinces")
    suspend fun getProvinces(@Query("todas") all: Boolean?): Response<ProvinceResponse>

    @GET("residences/towns/{idProvince}")
    suspend fun getTowns(
        @Path("idProvince") idProvince: Int,
        @Query("todas") all: Boolean?
    ): Response<TownResponse>

    @GET("residences/rooms")
    suspend fun getRooms(): Response<RoomResponse>

    @GET("residences/prices")
    suspend fun getPrices(): Response<PriceResponse>
}
