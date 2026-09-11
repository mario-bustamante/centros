package com.altoservicios.centros.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.profile.components.ProfileContent

@Composable
fun ProfileScreen() {
    Scaffold() { paddingValues ->
        ProfileContent(paddingValues)
    }
}