package com.altoservicios.centros.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.RolesScreen
import com.altoservicios.centros.presentation.screens.admin.home.AdminHomeScreen
import com.altoservicios.centros.presentation.screens.client.home.ClientHomeScreen
import com.altoservicios.centros.presentation.screens.roles.RolesScreen

fun NavGraphBuilder.RolesNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.ROLES,
        startDestination = RolesScreen.Roles.route
    ) {

        composable(route = RolesScreen.Roles.route) {
            RolesScreen(navController)
        }

        composable(route = Graph.ADMIN) {
            AdminHomeScreen()
        }

        composable(route = Graph.CLIENT) {
            ClientHomeScreen(
                onLogout = {
                    navController.navigate(Graph.AUTH) {
                        popUpTo(Graph.ROOT) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}