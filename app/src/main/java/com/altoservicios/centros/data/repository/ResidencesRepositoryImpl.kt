package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.ResidenceService
import com.altoservicios.centros.domain.model.Dependence
import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.ResidencesPage
import com.altoservicios.centros.domain.model.ResidencesResponse
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.model.Sector
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import com.altoservicios.centros.domain.util.map

class ResidencesRepositoryImpl(
    private val service: ResidenceService
) : ResidencesRepository {

    override suspend fun getMyResidence(): Resource<Residence> =
        ResponseToRequest.call { service.getMyResidence() }.map { it.data ?: Residence() }

    override suspend fun updateMyResidence(
        residence: Residence,
        dependence: String,
        sector: String,
        room: String
    ): Resource<Residence> {
        val params = mutableMapOf(
            "dependence" to dependence,
            "sector" to sector,
            "room" to room,
        )
        residence.name?.let { params["name"] = it }
        residence.email?.let { params["email"] = it }
        residence.web?.let { params["web"] = it }
        residence.address?.let { params["address"] = it }
        residence.phone?.let { params["phone"] = it }
        residence.price?.let { params["price"] = it }
        residence.description?.let { params["description"] = it }
        residence.latitude?.let { params["latitude"] = it }
        residence.longitude?.let { params["longitude"] = it }
        residence.townId?.let { params["idtown"] = it }

        return ResponseToRequest.call { service.updateMyResidence(params) }.map { it.data ?: residence }
    }

    override suspend fun getMyResidenceRooms(): Resource<List<Room>> =
        ResponseToRequest.call { service.getMyResidenceRooms() }.map { it.data.orEmpty() }

    override suspend fun getMyResidenceSectors(): Resource<List<Sector>> =
        ResponseToRequest.call { service.getMyResidenceSectors() }.map { it.data.orEmpty() }

    override suspend fun getMyResidenceDependencies(): Resource<List<Dependence>> =
        ResponseToRequest.call { service.getMyResidenceDependencies() }.map { it.data.orEmpty() }

    override suspend fun getResidences(page: Int?, search: Search?): Resource<ResidencesPage> =
        ResponseToRequest.call { service.getResidences(page, search.toFilters()) }.map { it.toPage() }

    override suspend fun getResidencesMap(page: Int?, search: Search?): Resource<ResidencesPage> {
        val filters = search.toFilters() + ("mapa" to "true")
        return ResponseToRequest.call { service.getResidencesMap(page, filters) }.map { it.toPage() }
    }

    override suspend fun getProvinces(all: Boolean?): Resource<List<Province>> =
        ResponseToRequest.call { service.getProvinces(all) }.map { it.data.orEmpty() }

    override suspend fun getTowns(idProvince: Int, all: Boolean?): Resource<List<Town>> =
        ResponseToRequest.call { service.getTowns(idProvince, all) }.map { it.data.orEmpty() }

    override suspend fun getRooms(): Resource<List<Room>> =
        ResponseToRequest.call { service.getRooms() }.map { it.data.orEmpty() }

    override suspend fun getSectors(): Resource<List<Sector>> =
        ResponseToRequest.call { service.getSectors() }.map { it.data.orEmpty() }

    override suspend fun getDependencies(): Resource<List<Dependence>> =
        ResponseToRequest.call { service.getDependencies() }.map { it.data.orEmpty() }

    override suspend fun getPrices(): Resource<List<Price>> =
        ResponseToRequest.call { service.getPrices() }.map { it.data.orEmpty() }

    private fun ResidencesResponse.toPage() = ResidencesPage(
        nextPage = currentPage?.plus(1),
        residences = data.orEmpty()
    )

    private fun Search?.toFilters(): Map<String, String> {
        val filters = mutableMapOf<String, String>()
        this ?: return filters

        searchFor.takeIf { it.isNotBlank() }?.let { filters["buscar_por"] = it }
        province?.let { filters["provincia"] = it.toString() }
        town?.let { filters["municipio"] = it.toString() }
        price?.let { filters["precio"] = it.toString() }
        room?.let { filters["habitacion"] = it.toString() }
        sector?.let { filters["plaza"] = it.toString() }
        dependence?.let { filters["dependencia"] = it.toString() }
        latitude?.let { filters["latitud"] = it.toString() }
        longitude?.let { filters["longitud"] = it.toString() }
        return filters
    }
}
