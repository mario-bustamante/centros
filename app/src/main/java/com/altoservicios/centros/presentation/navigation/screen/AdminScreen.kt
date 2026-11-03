package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Photo

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
        route = "admin/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}

/** Pantallas que no aparecen en la barra inferior. */
object AdminRoute {
    const val PROFILE_UPDATE = "customer/profile/update"
}
