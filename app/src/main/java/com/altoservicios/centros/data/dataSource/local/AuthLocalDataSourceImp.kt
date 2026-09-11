package com.altoservicios.centros.data.dataSource.local

import com.altoservicios.centros.data.datastore.AuthDataStore
import com.altoservicios.centros.domain.model.AuthResponse
import kotlinx.coroutines.flow.Flow

class AuthLocalDataSourceImp constructor(private val authDataStore: AuthDataStore): AuthLocalDataSource {

    override suspend fun saveSession(authResponse: AuthResponse) = authDataStore.saveUser(authResponse)
    override suspend fun logout() = authDataStore.delete()
    override fun getSessionData(): Flow<AuthResponse> = authDataStore.getData()

}