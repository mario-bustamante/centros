package com.altoservicios.centros.data.repository

import com.altoservicios.centros.data.dataSource.remote.service.UserService
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.repository.UserRepository
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.domain.util.ResponseToRequest
import com.altoservicios.centros.domain.util.map

class UserRepositoryImpl(
    private val service: UserService
) : UserRepository {

    override suspend fun getUser(): Resource<User> =
        ResponseToRequest.call { service.getUser() }.map { it.data ?: User(name = "") }

    override suspend fun updateUser(user: User): Resource<User> =
        ResponseToRequest.call {
            service.updateUser(user.name, user.fullName, user.address, user.phone)
        }.map { it.data ?: user }
}
