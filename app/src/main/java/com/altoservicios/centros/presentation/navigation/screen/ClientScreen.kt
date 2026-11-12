package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ClientScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Residences: ClientScreen(
        route = "client/residences",
        title = "Mis Residencia",
        icon = Icons.AutoMirrored.Filled.List
    )

    object Residence: ClientScreen(
        route = "client/residence/{residenceId}",
        title = "Mi Residencia",
        icon = Icons.Default.Home
    ) {
        fun createRoute(residenceId: String): String = "client/residence/$residenceId"
    }

    object Rooms: ClientScreen(
        route = "client/rooms/{residenceId}",
        title = "Habitaciones",
        icon = Icons.AutoMirrored.Filled.List
    ) {
        fun createRoute(residenceId: String): String = "client/rooms/$residenceId"
    }

    object Profile: ClientScreen(
        route = "profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}