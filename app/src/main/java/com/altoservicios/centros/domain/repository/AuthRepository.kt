package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.util.Resource

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<AuthResponse>
}