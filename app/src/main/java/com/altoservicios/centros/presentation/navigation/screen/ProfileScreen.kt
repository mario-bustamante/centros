package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

object ProfileScreen : MenuScreen {
    override val route = "profile"
    override val title = "Perfil"
    override val icon: ImageVector = Icons.Default.Person

    const val PROFILE = "profile"
    const val PROFILE_UPDATE = "profile/update"
}
