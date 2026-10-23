package com.altoservicios.centros.domain.repository

import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.util.Resource

interface UserRepository {

    suspend fun getUser(): Resource<User>

    suspend fun updateUser(user: User): Resource<User>
}
