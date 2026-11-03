package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
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

    object Residence: AdminScreen(
        route = "admin/residence",
        title = "Mi Residencia",
        icon = Icons.Default.Home
    )

    object Photos: AdminScreen(
        route = "admin/photos",
        title = "Fotografías",
        icon = Icons.Default.Star
    )

    object Rooms: AdminScreen(
        route = "admin/rooms",
        title = "Habitaciones",
        icon = Icons.AutoMirrored.Filled.List
    )

    object Profile: AdminScreen(
        route = "admin/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}

/** Pantallas de la residencia que no aparecen en la barra inferior. */
object AdminRoute {
    const val PHOTO = "admin/photo?photo={photo}"
    const val PROFILE_UPDATE = "admin/profile/update"

    // Sin fotografia se abre la pantalla para crear una nueva
    fun photo(photo: Photo?) =
        if (photo == null) "admin/photo" else "admin/photo?photo=${Uri.encode(photo.toJson())}"
}
