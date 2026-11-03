package com.altoservicios.centros.domain.useCase.contact

import com.altoservicios.centros.domain.repository.ContactRepository

data class ContactUseCase(
    val getValues: GetContactValuesUseCase
)

class GetContactValuesUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke() = repository.getValues()
}
