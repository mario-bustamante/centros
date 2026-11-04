package com.altoservicios.centros.presentation.screens.auth.register

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
