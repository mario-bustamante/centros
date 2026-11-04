package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AdminScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Home: AdminScreen(
        route = "admin/residence",
        title = "Dashboard",
        icon = Icons.Default.Home
    )

    object Profile: AdminScreen(
        route = "profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}
