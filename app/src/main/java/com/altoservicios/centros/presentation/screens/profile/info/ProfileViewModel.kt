package com.altoservicios.centros.presentation.screens.profile.info

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.User
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    init {
        getSessionData()
    }

    fun getSessionData() = viewModelScope.launch {
        authUseCase.getSession().collect() { data ->
            user = data.user
        }
    }

    fun logout(onComplete: () -> Unit) = viewModelScope.launch {
        authUseCase.logout()
        onComplete()
    }

}