package com.altoservicios.centros.data.dataSource.remote.service

import com.altoservicios.centros.domain.model.DependenceResponse
import com.altoservicios.centros.domain.model.PriceResponse
import com.altoservicios.centros.domain.model.ProvinceResponse
import com.altoservicios.centros.domain.model.ResidenceResponse
import com.altoservicios.centros.domain.model.ResidencesResponse
import com.altoservicios.centros.domain.model.RoomResponse
import com.altoservicios.centros.domain.model.SectorResponse
import com.altoservicios.centros.domain.model.TownResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ResidenceService {

    /*********** MI RESIDENCIA ************/
    @GET("residence")
    suspend fun getMyResidence(): Response<ResidenceResponse>

    @PUT("residence")
    suspend fun updateMyResidence(@QueryMap params: Map<String, String>): Response<ResidenceResponse>

    @GET("rooms")
    suspend fun getMyResidenceRooms(): Response<RoomResponse>

    @GET("sectors")
    suspend fun getMyResidenceSectors(): Response<SectorResponse>

    @GET("dependencies")
    suspend fun getMyResidenceDependencies(): Response<DependenceResponse>

    /*********** RESIDENCIAS ************/
    @GET("care-homes")
    suspend fun getResidences(
        @Query("page") page: Int?,
        @QueryMap filters: Map<String, String>
    ): Response<ResidencesResponse>

    @GET("care-homes/map")
    suspend fun getResidencesMap(
        @Query("page") page: Int?,
        @QueryMap filters: Map<String, String>
    ): Response<ResidencesResponse>

    /*********** BUSCADOR ************/
    @GET("care-homes/provinces")
    suspend fun getProvinces(@Query("todas") all: Boolean?): Response<ProvinceResponse>

    @GET("care-homes/towns/{idProvince}")
    suspend fun getTowns(
        @Path("idProvince") idProvince: Int,
        @Query("todas") all: Boolean?
    ): Response<TownResponse>

    @GET("care-homes/rooms")
    suspend fun getRooms(): Response<RoomResponse>

    @GET("care-homes/sectors")
    suspend fun getSectors(): Response<SectorResponse>

    @GET("care-homes/dependencies")
    suspend fun getDependencies(): Response<DependenceResponse>

    @GET("care-homes/prices")
    suspend fun getPrices(): Response<PriceResponse>
}
