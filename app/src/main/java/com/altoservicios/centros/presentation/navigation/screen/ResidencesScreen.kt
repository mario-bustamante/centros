package com.altoservicios.centros.presentation.navigation.screen

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.altoservicios.centros.domain.model.Residence

sealed class ResidencesScreen(
    override var route: String,
    override val title: String,
    override val icon: ImageVector
) : MenuScreen {

    object Residences: ResidencesScreen(
        route = "residences",
        title = "Residencias",
        icon = Icons.Default.Search
    )

    object Map: ResidencesScreen(
        route = "residences/map",
        title = "Mapa",
        icon = Icons.Default.Place
    )

}

/** Pantallas del usuario que no aparecen en la barra inferior. */
object ResidencesRoute {
    const val SEARCH = "residences/search"
    const val DETAIL = "residences/residence/{residence}"

    fun detail(residence: Residence) = "residences/residence/${Uri.encode(residence.toJson())}"
}