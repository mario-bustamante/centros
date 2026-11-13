package com.altoservicios.centros.presentation.screens.roles

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.GenericTopBar
import com.altoservicios.centros.presentation.screens.home.components.HomeTopBar
import com.altoservicios.centros.presentation.screens.profile.info.components.ProfileContent
import com.altoservicios.centros.presentation.screens.roles.components.RolesContent

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RolesScreen(
    navController: NavHostController,
    onLogout: () -> Unit = {}
) {

    HomeTopBar(
        navController = navController,
        content = { paddingValues ->
            Scaffold (
                Modifier.padding(paddingValues),
                topBar = {
                    GenericTopBar()
                }
            ) { paddingValues ->
                RolesContent(paddingValues, navController, onLogout = onLogout)
            }
        }
    )
}

