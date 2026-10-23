package com.altoservicios.centros.presentation.screens.profile.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import com.altoservicios.centros.domain.useCase.user.UserUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var message by mutableStateOf("")
    var finished by mutableStateOf(false)
        private set

    private var session: AuthResponse? = null

    init {
        viewModelScope.launch {
            session = authUseCase.getSession().first()
            user = session?.user
        }
    }

    fun onUserChange(newUser: User) {
        user = newUser
    }

    fun save(successMessage: String) {
        val current = user ?: return
        isLoading = true
        viewModelScope.launch {
            when (val result = userUseCase.updateUser(current)) {
                is Resource.Success -> {
                    // Se actualiza la sesion guardada para que el perfil muestre los nuevos datos
                    session?.let { authUseCase.saveSession(it.copy(user = current.copy(
                        name = result.data.name.ifBlank { current.name },
                        fullName = result.data.fullName ?: current.fullName,
                        address = result.data.address ?: current.address,
                        phone = result.data.phone ?: current.phone
                    ))) }
                    message = successMessage
                    finished = true
                }
                is Resource.Failure -> message = result.message
                Resource.Loading -> Unit
            }
            isLoading = false
        }
    }
}
