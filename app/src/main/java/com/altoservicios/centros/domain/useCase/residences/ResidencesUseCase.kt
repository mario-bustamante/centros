package com.altoservicios.centros.domain.useCase.residences

import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.domain.model.Search
import com.altoservicios.centros.domain.repository.ResidencesRepository

data class ResidencesUseCase(
    val getResidences: GetResidencesUseCase,
    val getResidencesMap: GetResidencesMapUseCase,
    val getProvinces: GetProvincesUseCase,
    val getTowns: GetTownsUseCase,
    val getRooms: GetRoomsUseCase,
    val getSectors: GetSectorsUseCase,
    val getDependencies: GetDependenciesUseCase,
    val getPrices: GetPricesUseCase,
    val getMyResidence: GetMyResidenceUseCase,
    val updateMyResidence: UpdateMyResidenceUseCase,
    val getMyRooms: GetMyRoomsUseCase,
    val getMySectors: GetMySectorsUseCase,
    val getMyDependencies: GetMyDependenciesUseCase,
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

class GetSectorsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getSectors()
}

class GetDependenciesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getDependencies()
}

class GetPricesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getPrices()
}

class GetMyResidenceUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getMyResidence()
}

class UpdateMyResidenceUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke(residence: Residence, dependence: String, sector: String, room: String) =
        repository.updateMyResidence(residence, dependence, sector, room)
}

class GetMyRoomsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getMyResidenceRooms()
}

class GetMySectorsUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getMyResidenceSectors()
}

class GetMyDependenciesUseCase(private val repository: ResidencesRepository) {
    suspend operator fun invoke() = repository.getMyResidenceDependencies()
}
