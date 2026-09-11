package com.altoservicios.centros.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    fun logout(onComplete: () -> Unit) = viewModelScope.launch {
        authUseCase.logout()
        onComplete()
    }

}