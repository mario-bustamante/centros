package com.altoservicios.centros.domain.useCase.residences

import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.RoomAvailability
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.repository.ResidencesRepository

data class ResidencesUseCase(
    val getResidences: GetResidencesUseCase,
    val getResidencesMap: GetResidencesMapUseCase,
    val getProvinces: GetProvincesUseCase,
    val getTowns: GetTownsUseCase,
    val getRooms: GetRoomsUseCase,
    val getPrices: GetPricesUseCase,
    val getMyResidences: GetMyResidencesUseCase,
    val getResidenceById: GetResidenceByIdUseCase,
    val updateMyResidence: UpdateMyResidenceUseCase,
    val getMyRooms: GetMyRoomsUseCase,
    val updateMyRooms: UpdateMyRoomsUseCase,
)

class GetResidencesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(page: Int?, search: Search?) = repository.getResidences(page, search)
}

class GetResidencesMapUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(page: Int?, search: Search?) = repository.getResidencesMap(page, search)
}

class GetProvincesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(all: Boolean? = null) = repository.getProvinces(all)
}

class GetTownsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(idProvince: Int, all: Boolean? = null) = repository.getTowns(idProvince, all)
}

class GetRoomsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getRooms()
}

class GetPricesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getPrices()
}

class GetMyResidencesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getMyResidences()
}

class GetResidenceByIdUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(idResidence: String) = repository.getResidenceById(idResidence)
}

class UpdateMyResidenceUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(residence: Residence, room: String) =
        repository.updateMyResidence(residence, room)
}

class GetMyRoomsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(idResidence: String) = repository.getMyResidenceRooms(idResidence)
}

class UpdateMyRoomsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(rooms: List<RoomAvailability>) = repository.updateMyResidenceRooms(rooms)
}