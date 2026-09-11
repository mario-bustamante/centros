package com.altoservicios.centros.domain.useCase.auth

data class AuthUseCase(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val saveSession: SaveSessionUseCase,
    val getSessionUseCase: GetSessionDataUseCase,
    val logout: LogoutUseCase
)
