package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeScreen(
    var route: String,
    val title: String,
    val icon: ImageVector
) {

    object Contact: HomeScreen(
        route = "contact",
        title = "Contacto",
        icon = Icons.Default.Info
    )

    object Residences: HomeScreen(
        route = "residences",
        title = "Residencias",
        icon = Icons.Default.Search
    )

    object Auth: HomeScreen(
        route = "login",
        title = "Login",
        icon = Icons.Default.Person
    )
}