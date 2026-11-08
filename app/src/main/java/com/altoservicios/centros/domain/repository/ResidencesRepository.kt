package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.ResidencesPage
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.RoomAvailability
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.util.Resource

interface ResidencesRepository {

    suspend fun getMyResidences(): Resource<List<Residence>>

    suspend fun getResidenceById(idResidence: String): Resource<Residence>

    suspend fun updateMyResidence(
        residence: Residence,
        room: String
    ): Resource<Residence>

    suspend fun getMyResidenceRooms(idResidence: String): Resource<List<Room>>

    suspend fun updateMyResidenceRooms(rooms: List<RoomAvailability>): Resource<Int>

    suspend fun getResidences(page: Int?, search: Search?): Resource<ResidencesPage>

    suspend fun getResidencesMap(page: Int?, search: Search?): Resource<ResidencesPage>

    suspend fun getProvinces(all: Boolean? = null): Resource<List<Province>>

    suspend fun getTowns(idProvince: Int, all: Boolean? = null): Resource<List<Town>>

    suspend fun getRooms(): Resource<List<Room>>

    suspend fun getPrices(): Resource<List<Price>>
}
