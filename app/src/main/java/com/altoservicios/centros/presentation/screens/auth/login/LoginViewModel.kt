package com.altoservicios.centros.presentation.screens.auth.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.core.Config
import com.altoservicios.centros.domain.model.AuthResponse
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import com.altoservicios.centros.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf("")
        private set

    var loginResponse by mutableStateOf<Response<AuthResponse>?>(null)

    fun onEmailInput(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordInput(password: String) {
        state = state.copy(password = password)
    }

    fun login() = viewModelScope.launch {
        if(isValidForm()) {
            loginResponse = Response.Loading

            Log.d("LoginViewModel", "Result: ${Config.BASE_URL}")

            val result = authUseCase.login(state.email, state.password)
            loginResponse = result

            Log.d("LoginViewModel", "Result: ${loginResponse}")
        }
    }

    fun isValidForm(): Boolean {

        if(!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errorMessage = "El email no es válido"
            return false

        } else if(state.password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        errorMessage = ""
        return true

    }
}