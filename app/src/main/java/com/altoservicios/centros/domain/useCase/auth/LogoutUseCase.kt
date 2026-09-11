package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.repository.AuthRepository

class LogoutUseCase constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logout()
}