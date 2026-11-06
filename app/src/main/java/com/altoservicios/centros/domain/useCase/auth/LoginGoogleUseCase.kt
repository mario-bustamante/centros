package com.altoservicios.centros.domain.useCase.auth

import com.altoservicios.centros.domain.repository.AuthRepository

class LoginGoogleUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(idToken: String) = repository.loginGoogle(idToken)

}
