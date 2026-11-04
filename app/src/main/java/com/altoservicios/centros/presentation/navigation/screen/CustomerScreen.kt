package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CustomerScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Residences: CustomerScreen(
        route = "residences",
        title = "Residencias",
        icon = Icons.Default.Search
    )

    object Map: CustomerScreen(
        route = "residences/map",
        title = "Mapa",
        icon = Icons.Default.Place
    )

    object Profile: CustomerScreen(
        route = "profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}