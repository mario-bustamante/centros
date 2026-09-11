package com.altoservicios.centros.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ClientScreen(
    var route: String,
    val title: String,
    val icon: ImageVector
) {

    object CategoryList: ClientScreen(
        route = "client/category/list",
        title = "Categorias",
        icon = Icons.Default.List
    )

    object ProductList: ClientScreen(
        route = "client/product/list",
        title = "Productos",
        icon = Icons.Default.ThumbUp
    )

    object Profile: ClientScreen(
        route = "client/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}