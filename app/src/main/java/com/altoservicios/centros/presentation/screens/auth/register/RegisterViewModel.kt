package com.altoservicios.centros.presentation.screens.auth.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import com.altoservicios.centros.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    var errorMessage by mutableStateOf("")

    var registerResponse by mutableStateOf<Resource<AuthResponse>?>(null)
        private set


    fun onNameInput(name: String) {
        state = state.copy(name = name)
    }

    fun onEmailInput(email: String) {
        state = state.copy(email = email)
    }

    fun onPhoneInput(phone: String) {
        state = state.copy(phone = phone)
    }

    fun onPasswordInput(password: String) {
        state = state.copy(password = password)
    }

    fun onConfirmPasswordInput(confirmPassword: String) {
        state = state.copy(confirmPassword = confirmPassword)
    }

    fun register() = viewModelScope.launch {
        if(isValidForm()) {
            val user = User(
                name = state.name,
                email = state.email,
                phone = state.phone,
                password = state.password
            )

            registerResponse = Resource.Loading
            val result = authUseCase.register(user)
            registerResponse = result
        }
    }

    fun isValidForm(): Boolean {

        if(state.name != "") {
            errorMessage = "El nombre no es válido"
            return false
        } else if(!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errorMessage = "El email no es válido"
            return false
        } else if(!Patterns.PHONE.matcher(state.phone).matches()) {
            errorMessage = "El email no es válido"
            return false
        } else if(state.password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return false
        } else if(state.confirmPassword.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }
}