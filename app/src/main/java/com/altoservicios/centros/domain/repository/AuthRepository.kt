package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<AuthResponse>

    suspend fun register(user: User): Resource<AuthResponse>

    suspend fun saveSession(authResponse: AuthResponse)

    suspend fun logout()

    fun getSessionData(): Flow<AuthResponse>
}