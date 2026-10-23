package com.altoservicios.centros.domain.useCase.user

import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.repository.UserRepository

data class UserUseCase(
    val getUser: GetUserUseCase,
    val updateUser: UpdateUserUseCase,
)

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.getUser()
}

class UpdateUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) = repository.updateUser(user)
}
