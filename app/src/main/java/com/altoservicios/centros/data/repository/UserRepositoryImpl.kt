package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.UserService
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.repository.UserRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest

class UserRepositoryImpl(
    private val service: UserService
) : UserRepository {

    override suspend fun getUser(): Resource<User> =
        when (val response = ResponseToRequest.call { service.getUser() }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene los datos del usuario")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }

    override suspend fun updateUser(user: User): Resource<User> =
        when (val response = ResponseToRequest.call {
            service.updateUser(user.name, user.fullName, user.address, user.phone)
        }) {
            is Resource.Success -> response.data.data?.let { Resource.Success(it) }
                ?: Resource.Failure("La respuesta no contiene el usuario actualizado")
            is Resource.Failure -> Resource.Failure(response.message)
            Resource.Loading -> Resource.Loading
        }
}
