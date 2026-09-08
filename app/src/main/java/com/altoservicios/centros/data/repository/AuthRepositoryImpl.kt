package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.util.Response

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Response<AuthResponse> {
        try {
            val result = authRemoteDataSource.login(email, password)

            if (result.isSuccessful) {
                val body = result.body()
                    ?: return Response.Failure(Exception("La respuesta de autenticación está vacía"))
                return Response.Success(body)
            }

            return Response.Failure(
                Exception("Error HTTP ${result.code()}: ${result.message()}")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Response.Failure(e)
        }
    }

}