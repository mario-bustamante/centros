package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.ErrorResponse
import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.util.ConvertErrorBody
import com.altoservicios.centros.domain.util.Resource
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Resource<AuthResponse> {
        try {
            val result = authRemoteDataSource.login(email, password)

            if (result.isSuccessful) {
                val body = result.body() ?: return Resource.Failure("La respuesta de autenticación está vacía")
                return Resource.Success(body)
            } else {
                val errorResponse: ErrorResponse? = ConvertErrorBody.convertErrorBody(result.errorBody())
                return Resource.Failure(errorResponse?.message ?: "Hubo un error")
            }


        } catch (e: HttpException) {
            e.printStackTrace()
            return Resource.Failure(e.message ?: "Hubo un error en la peticion http")
        } catch (e: IOException) {
            e.printStackTrace()
            return Resource.Failure(e.message ?: "Hubo un error, verifica la conexion a internet")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure(e.message ?: "Hubo un error")
        }
    }

}