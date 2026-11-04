package com.altoservicios.centros.presentation.screens.home.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen

val homeBottomBarScreens = listOf(
    HomeScreen.Residences,
    HomeScreen.Contact,
    HomeScreen.Auth
)

@Composable
fun HomeBottomBar(
    selectedRoute: String?,
    onItemClick: (HomeScreen) -> Unit
) {
    NavigationBar {
        homeBottomBarScreens.forEach { screen ->
            NavigationBarItem(
                selected = selectedRoute == screen.route,
                onClick = { onItemClick(screen) },
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(text = screen.title) }
            )
        }
    }
}
