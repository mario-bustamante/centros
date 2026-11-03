package com.altoservicios.centros.presentation.screens.home.contact

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.MenuHomeTopBar
import com.altoservicios.centros.presentation.screens.home.contact.components.HomeContactContent

@Composable
fun HomeContactScreen(
    navController: NavHostController,
    vm: HomeContactViewModel = hiltViewModel()
) {
    MenuHomeTopBar(
        title = "Contacto",
        navController = navController,
        actions = {},
        onLogout = null,
        content = { paddingValues ->
            HomeContactContent(paddingValues, vm)
        }
    )
}
