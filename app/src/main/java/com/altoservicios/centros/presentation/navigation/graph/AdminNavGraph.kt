package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.altoservicios.centros.presentation.screens.admin.residence.AdminResidenceViewModel
import com.altoservicios.centros.presentation.screens.admin.rooms.AdminRoomsScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun AdminNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Compartido por Mi Residencia y Habitaciones, que se guardan juntas
    val residenceViewModel: AdminResidenceViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.ADMIN,
        startDestination = AdminScreen.Residence.route,
        modifier = modifier
    ) {
        composable(route = AdminScreen.Residence.route) {
            AdminResidenceScreen(vm = residenceViewModel, navController = navController)
        }

        composable(route = AdminScreen.Rooms.route) {
            AdminRoomsScreen(vm = residenceViewModel, navController = navController)
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
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(AdminRoute.PROFILE_UPDATE) })
        }

        composable(route = AdminRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }
    }
}
