package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.AdminRoute
import com.altoservicios.centros.presentation.navigation.screen.AdminScreen
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.screens.admin.AdminViewModel
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun AdminNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val adminViewModel: AdminViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.ADMIN,
        startDestination = AdminScreen.Profile.route,
        modifier = modifier
    ) {

        composable(route = AdminScreen.Profile.route) {
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(ClientRoute.PROFILE_UPDATE) })
        }

        composable(route = AdminRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }
    }
}
