package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(user: User) = repository.register(user)
}