package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.ui.graphics.vector.ImageVector

/** Destino con entrada en el menu lateral. */
interface MenuScreen {
    val route: String
    val title: String
    val icon: ImageVector
}
