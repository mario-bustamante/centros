package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.Dependence
import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.ResidencesPage
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.model.Sector
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.util.Resource

interface ResidencesRepository {

    suspend fun getMyResidence(): Resource<Residence>

    suspend fun updateMyResidence(
        residence: Residence,
        dependence: String,
        sector: String,
        room: String
    ): Resource<Residence>

    suspend fun getMyResidenceRooms(): Resource<List<Room>>

    suspend fun getMyResidenceSectors(): Resource<List<Sector>>

    suspend fun getMyResidenceDependencies(): Resource<List<Dependence>>

    suspend fun getResidences(page: Int?, search: Search?): Resource<ResidencesPage>

    suspend fun getResidencesMap(page: Int?, search: Search?): Resource<ResidencesPage>

    suspend fun getProvinces(all: Boolean? = null): Resource<List<Province>>

    suspend fun getTowns(idProvince: Int, all: Boolean? = null): Resource<List<Town>>

    suspend fun getRooms(): Resource<List<Room>>

    suspend fun getSectors(): Resource<List<Sector>>

    suspend fun getDependencies(): Resource<List<Dependence>>

    suspend fun getPrices(): Resource<List<Price>>
}
