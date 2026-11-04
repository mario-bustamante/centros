package com.altoservicios.centros.presentation.screens.admin.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.graph.AdminNavGraph
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.navigation.screen.AdminScreen
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBar
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel


@Composable
fun AdminHomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val screens = listOf(
        AdminScreen.Profile
    )

    MenuTopBar(
        title = "Administrador",
        drawerState = drawerState,
        screens = screens,
        navController = navController,
        actions = {},
        onLogout = { profileViewModel.logout(onLogout) },
        content = { paddingValues ->
            Scaffold(
                Modifier.padding(paddingValues),
                bottomBar = { ClientBottomBar(navController = navController) }
            ) { paddingValues ->
                AdminNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                )
            }
        }
    )
}