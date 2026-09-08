package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)

}