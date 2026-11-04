package com.altoservicios.centros.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

fun NavGraphBuilder.ProfileNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreen.route
    ) {

        composable(route = ProfileScreen.PROFILE) {
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(ProfileScreen.PROFILE_UPDATE) })
        }

        composable(route = ProfileScreen.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }
    }
}