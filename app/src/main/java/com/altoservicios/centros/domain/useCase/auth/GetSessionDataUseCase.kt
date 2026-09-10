package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.repository.AuthRepository

class GetSessionDataUseCase constructor(private val repository: AuthRepository) {

    operator fun invoke() = repository.getSessionData()

}