package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Residence

sealed class CustomerScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Residences: CustomerScreen(
        route = "customer/residences",
        title = "Residencias",
        icon = Icons.Default.Search
    )

    object Map: CustomerScreen(
        route = "customer/map",
        title = "Mapa",
        icon = Icons.Default.Place
    )

    object Profile: CustomerScreen(
        route = "customer/profile",
        title = "Perfil",
        icon = Icons.Default.Person
    )

}

/** Pantallas del usuario que no aparecen en la barra inferior. */
object CustomerRoute {
    const val SEARCH = "customer/search"
    const val DETAIL = "customer/residence/{residence}"
    const val PROFILE_UPDATE = "customer/profile/update"

    fun detail(residence: Residence) = "customer/residence/${Uri.encode(residence.toJson())}"
}