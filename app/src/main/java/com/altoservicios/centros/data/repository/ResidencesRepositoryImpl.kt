package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.ResidenceService
import com.altoservicios.centros.data.dataSource.remote.service.ResidencesService
import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.Province
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.ResidencesPage
import com.altoservicios.centros.domain.model.ResidencesResponse
import com.altoservicios.centros.domain.model.Room
import com.altoservicios.centros.domain.model.RoomAvailability
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.model.Town
import com.altoservicios.centros.domain.model.UpdateRoomsRequest
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import com.altoservicios.centros.domain.util.map

class ResidencesRepositoryImpl(
    private val residenceService: ResidenceService,
    private val residencesService: ResidencesService
) : ResidencesRepository {

    override suspend fun getMyResidences(): Resource<List<Residence>> =
        when (val response = ResponseToRequest.call { residenceService.getMyResidences() }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta de residencias no contiene datos")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun getResidenceById(idResidence: String): Resource<Residence> =
        when (val response = ResponseToRequest.call { residenceService.getResidenceById(idResidence) }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene la residencia")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun updateMyResidence(residence: Residence): Resource<Residence> {
        val params = mutableMapOf("residence_id" to residence.id)
        residence.name?.let { params["name"] = it }
        residence.email?.let { params["email"] = it }
        residence.web?.let { params["web"] = it }
        residence.address?.let { params["address"] = it }
        residence.phone?.let { params["phone"] = it }
        residence.description?.let { params["description"] = it }
        residence.latitude?.let { params["latitude"] = it }
        residence.longitude?.let { params["longitude"] = it }
        residence.townId?.let { params["idtown"] = it }

        return when (val response = ResponseToRequest.call {
            residenceService.updateMyResidence(params)
        }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene la residencia actualizada")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun getMyResidenceRooms(idResidence: String): Resource<List<Room>> =
        when (val response = ResponseToRequest.call { residenceService.getMyResidenceRooms(idResidence) }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene las habitaciones")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun updateMyResidenceRooms(
        residenceId: String,
        rooms: List<RoomAvailability>
    ): Resource<Int> =
        when (val response = ResponseToRequest.call {
            residenceService.updateMyResidenceRooms(UpdateRoomsRequest(residenceId, rooms))
        }) {
            is Resource.Success -> response.data.data?.updated?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene el total de habitaciones actualizadas")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun getResidences(page: Int?, search: Search?): Resource<ResidencesPage> =
        when (val response = ResponseToRequest.call {
            residencesService.getResidences(page, search.toFilters())
        }) {
            is Resource.Success -> response.data.toPage()
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun getResidencesMap(page: Int?, search: Search?): Resource<ResidencesPage> {
        val hasCoordinates = search?.latitude != null && search.longitude != null
        val filters = search.toFilters() + ("mapa" to hasCoordinates.toString())
        return when (val response = ResponseToRequest.call {
            residencesService.getResidencesMap(page, filters)
        }) {
            is Resource.Success -> response.data.toPage()
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }
    }

    override suspend fun getProvinces(all: Boolean?): Resource<List<Province>> =
        ResponseToRequest.call { residencesService.getProvinces(all) }.map { it.data.orEmpty() }

    override suspend fun getTowns(idProvince: Int, all: Boolean?): Resource<List<Town>> =
        ResponseToRequest.call { residencesService.getTowns(idProvince, all) }.map { it.data.orEmpty() }

    override suspend fun getRooms(): Resource<List<Room>> =
        ResponseToRequest.call { residencesService.getRooms() }.map { it.data.orEmpty() }

    override suspend fun getPrices(): Resource<Price> =
        ResponseToRequest.call { residencesService.getPrices() }.map { 
            it.data?.firstOrNull() ?: Price()
        }

    private fun ResidencesResponse.toPage(): Resource<ResidencesPage> {
        val residences = data
            ?: return Resource.Failure("La respuesta no contiene el listado de residencias")
        val pagination = meta
            ?: return Resource.Failure("La respuesta no contiene los metadatos de paginación")

        val currentPage = pagination.currentPage
            ?: return Resource.Failure("La respuesta no indica la página actual")
        val lastPage = pagination.lastPage
            ?: return Resource.Failure("La respuesta no indica la última página")

        return Resource.Success(
            ResidencesPage(
                nextPage = (currentPage + 1).takeIf { currentPage < lastPage },
                residences = residences
            )
        )
    }

    private fun Search?.toFilters(): Map<String, String> {
        val filters = mutableMapOf<String, String>()
        this ?: return filters

        searchFor.takeIf { it.isNotBlank() }?.let { filters["buscar_por"] = it }
        province?.let { filters["provincia"] = it.toString() }
        town?.let { filters["municipio"] = it.toString() }
        price?.let { filters["precio"] = it.toString() }
        room?.let { filters["habitacion"] = it.toString() }
        latitude?.let { filters["latitud"] = it.toString() }
        longitude?.let { filters["longitud"] = it.toString() }
        return filters
    }
}
