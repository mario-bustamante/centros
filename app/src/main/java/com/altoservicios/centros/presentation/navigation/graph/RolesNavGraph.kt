package com.altoservicios.centros.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.RolesScreen
import com.altoservicios.centros.presentation.screens.admin.home.AdminHomeScreen
import com.altoservicios.centros.presentation.screens.client.home.ClientHomeScreen
import com.altoservicios.centros.presentation.screens.customer.home.CustomerHomeScreen
import com.altoservicios.centros.presentation.screens.roles.RolesScreen

fun NavGraphBuilder.RolesNavGraph(navController: NavHostController) {
    fun navigateHomeAfterLogout() {
        navController.navigate(Graph.HOME) {
            popUpTo(Graph.HOME) { inclusive = true }
            launchSingleTop = true
        }
    }

    navigation(
        route = Graph.ROLES,
        startDestination = RolesScreen.Roles.route
    ) {

        composable(route = RolesScreen.Roles.route) {
            RolesScreen(navController)
        }

        composable(route = Graph.ADMIN) {
            AdminHomeScreen(
                onLogout = {
                    navigateHomeAfterLogout()
                }
            )

        }

        composable(route = Graph.CLIENT) {
            ClientHomeScreen(
                onLogout = {
                    navigateHomeAfterLogout()
            })
        }

        composable(route = Graph.CUSTOMER) {
            CustomerHomeScreen(
                onLogout = {
                    navigateHomeAfterLogout()
                }
            )
        }
    }
}