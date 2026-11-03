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
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.photo.ClientPhotoScreen
import com.altoservicios.centros.presentation.screens.client.photo.ClientPhotosScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceViewModel
import com.altoservicios.centros.presentation.screens.client.rooms.ClientRoomsScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun ClientNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Compartido por Mi Residencia y Habitaciones, que se guardan juntas
    val residenceViewModel: ClientResidenceViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.CLIENT,
        startDestination = ClientScreen.Residence.route,
        modifier = modifier
    ) {
        composable(route = ClientScreen.Residence.route) {
            ClientResidenceScreen(vm = residenceViewModel, navController = navController)
        }

        composable(route = ClientScreen.Rooms.route) {
            ClientRoomsScreen(vm = residenceViewModel, navController = navController)
        }

        composable(route = ClientScreen.Photos.route) {
            ClientPhotosScreen(navController)
        }

        composable(
            route = ClientRoute.PHOTO,
            arguments = listOf(
                navArgument("photo") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ClientPhotoScreen(navController)
        }

        composable(route = ClientScreen.Profile.route) {
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(ClientRoute.PROFILE_UPDATE) })
        }

        composable(route = ClientRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }
    }
}
