package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.ErrorResponse
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.util.ConvertErrorBody
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Resource<AuthResponse> = ResponseToRequest.send(
        authRemoteDataSource.login(email, password)
    )

    override suspend fun register(user: User): Resource<AuthResponse> = ResponseToRequest.send(
        authRemoteDataSource.register(user)
    )
}