package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceScreen
import com.altoservicios.centros.presentation.screens.client.residence.ClientResidenceViewModel
import com.altoservicios.centros.presentation.screens.client.rooms.ClientRoomsScreen

@Composable
fun ClientNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
   val clientViewModel: ClientResidenceViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.CLIENT,
        startDestination = ClientScreen.Residence.route,
        modifier = modifier
    ) {
        composable(route = ClientScreen.Residence.route) {
            ClientResidenceScreen(vm = clientViewModel)
        }

        composable(route = ClientScreen.Rooms.route) {
            ClientRoomsScreen(vm = clientViewModel)
        }

        PhotosNavGraph(navController)
        ProfileNavGraph(navController)
    }
}
