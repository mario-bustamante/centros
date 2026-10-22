package com.altoservicios.centros.presentation.screens.profile.update

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.profile.update.components.ProfileUpdateContent

@Composable
fun ProfileUpdateScreen() {
    Scaffold() { paddingValues ->
        ProfileUpdateContent(paddingValues)
    }
}