package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.AdminRoute
import com.altoservicios.centros.presentation.navigation.screen.AdminScreen
import com.altoservicios.centros.presentation.screens.admin.photo.AdminPhotoScreen
import com.altoservicios.centros.presentation.screens.admin.photo.AdminPhotosScreen
import com.altoservicios.centros.presentation.screens.admin.residence.AdminResidenceScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun AdminNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        route = Graph.ADMIN,
        startDestination = AdminScreen.Residence.route,
        modifier = modifier
    ) {
        composable(route = AdminScreen.Residence.route) {
            AdminResidenceScreen()
        }

        composable(route = AdminScreen.Photos.route) {
            AdminPhotosScreen(navController)
        }

        composable(
            route = AdminRoute.PHOTO,
            arguments = listOf(
                navArgument("photo") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            AdminPhotoScreen(navController)
        }

        composable(route = AdminScreen.Profile.route) {
            ProfileScreen(onEditProfile = { navController.navigate(AdminRoute.PROFILE_UPDATE) })
        }

        composable(route = AdminRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }
    }
}
