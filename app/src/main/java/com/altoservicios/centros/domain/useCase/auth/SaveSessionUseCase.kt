package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.repository.AuthRepository

class SaveSessionUseCase constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(authResponse: AuthResponse) = repository.saveSession(authResponse)

}