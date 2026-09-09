package com.altoservicios.centros.domain.util

import com.altoservicios.centros.domain.model.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ResponseToRequest {

    fun<T> send(result: Response<T>): Resource<T> {
        try {

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