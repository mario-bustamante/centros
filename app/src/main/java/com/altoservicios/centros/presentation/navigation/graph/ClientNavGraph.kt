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
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceViewModel
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesScreen
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel
import com.altoservicios.centros.presentation.screens.client.rooms.ClientRoomsScreen

@Composable
fun ClientNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val clientResidencesViewModel: ClientResidencesViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.CLIENT,
        startDestination = ClientScreen.Residences.route,
        modifier = modifier
    ) {

        composable(route = ClientScreen.Residences.route) {
            ClientResidencesScreen(navController,clientResidencesViewModel)
        }

        composable(
            route = ClientScreen.Residence.route,
            arguments = listOf(
                navArgument("residenceId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val clientViewModel: ClientResidenceViewModel = hiltViewModel()
            ClientResidenceScreen(vm = clientViewModel)
        }

        composable(
            route = ClientScreen.Rooms.route,
            arguments = listOf(
                navArgument("residenceId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val clientViewModel: ClientResidenceViewModel = hiltViewModel()
            ClientRoomsScreen(vm = clientViewModel)
        }

        PhotosNavGraph(navController)
        ProfileNavGraph(navController)
    }
}
