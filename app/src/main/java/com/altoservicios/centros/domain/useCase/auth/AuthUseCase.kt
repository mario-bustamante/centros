package com.altoservicios.centros.domain.useCase.auth

data class AuthUseCase(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val saveSession: SaveSessionUseCase,
    val getSession: GetSessionDataUseCase,
    val logout: LogoutUseCase
)
