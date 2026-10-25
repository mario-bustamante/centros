package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Residence

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

    object Map: HomeScreen(
        route = "map",
        title = "Mapa",
        icon = Icons.Default.Place
    )

    object Auth: HomeScreen(
        route = "login",
        title = "Login",
        icon = Icons.Default.Person
    )

}

/** Pantallas del home que no aparecen en la barra inferior. */
object HomeRoute {
    const val SEARCH = "client/search"
    const val DETAIL = "client/residence/{residence}"
    const val RESIDENCE_MAP = "client/residence/map/{residence}"

    fun detail(residence: Residence) = "client/residence/${Uri.encode(residence.toJson())}"

    fun residenceMap(residence: Residence) = "client/residence/map/${Uri.encode(residence.toJson())}"
}