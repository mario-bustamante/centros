package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Photo

sealed class ClientScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Residence: ClientScreen(
        route = "client/residence",
        title = "Mi Residencia",
        icon = Icons.Default.Home
    )

    object Photos: ClientScreen(
        route = "client/photos",
        title = "Fotografías",
        icon = Icons.Default.Star
    )

    object Rooms: ClientScreen(
        route = "client/rooms",
        title = "Habitaciones",
        icon = Icons.AutoMirrored.Filled.List
    )

    object Profile: ClientScreen(
        route = "client/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )
}

/** Pantallas de la residencia que no aparecen en la barra inferior. */
object ClientRoute {
    const val PHOTO = "client/photo?photo={photo}"
    const val PROFILE_UPDATE = "client/profile/update"

    // Sin fotografia se abre la pantalla para crear una nueva
    fun photo(photo: Photo?) =
        if (photo == null) "client/photo" else "client/photo?photo=${Uri.encode(photo.toJson())}"
}
