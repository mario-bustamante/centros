package com.altoservicios.centros.presentation.screens.profile.info

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.screens.profile.info.components.ProfileContent

@Composable
fun ProfileScreen(onEditProfile: () -> Unit) {
    Scaffold() { paddingValues ->
        ProfileContent(paddingValues, onEditProfile)
    }
}
