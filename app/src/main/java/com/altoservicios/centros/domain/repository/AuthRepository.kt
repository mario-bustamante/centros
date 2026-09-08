package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.util.Response

interface AuthRepository {

    suspend fun login(email: String, password: String): Response<AuthResponse>
}