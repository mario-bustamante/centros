package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactScreen
import com.altoservicios.centros.presentation.screens.residences.ResidencesViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val residencesViewModel: ResidencesViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Graph.RESIDENCES,
        modifier = modifier
    ) {

        composable(route = HomeScreen.Contact.route) {
            HomeContactScreen(navController)
        }

        ResidencesNavGraph(navController = navController, residencesViewModel = residencesViewModel)
        AuthNavGraph(navController = navController, rootNavController = rootNavController)
    }
}
