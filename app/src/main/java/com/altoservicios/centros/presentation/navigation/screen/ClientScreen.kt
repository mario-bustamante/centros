package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Residence

sealed class ClientScreen(
    var route: String,
    val title: String,
    val icon: ImageVector
) {

    object Residences: ClientScreen(
        route = "client/residences",
        title = "Residencias",
        icon = Icons.Default.Search
    )

    object Map: ClientScreen(
        route = "client/map",
        title = "Mapa",
        icon = Icons.Default.Place
    )

    object Profile: ClientScreen(
        route = "client/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )

}

/** Pantallas del cliente que no aparecen en la barra inferior. */
object ClientRoute {
    const val SEARCH = "client/search"
    const val DETAIL = "client/residence/{residence}"
    const val RESIDENCE_MAP = "client/residence/map/{residence}"
    const val PROFILE_UPDATE = "client/profile/update"

    fun detail(residence: Residence) = "client/residence/${Uri.encode(residence.toJson())}"

    fun residenceMap(residence: Residence) = "client/residence/map/${Uri.encode(residence.toJson())}"
}