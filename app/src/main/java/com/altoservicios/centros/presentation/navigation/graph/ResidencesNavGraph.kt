package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.CustomerScreen
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen
import com.altoservicios.centros.presentation.navigation.screen.ResidencesRoute
import com.altoservicios.centros.presentation.screens.residences.ResidencesViewModel
import com.altoservicios.centros.presentation.screens.residences.detail.ResidenceDetailScreen
import com.altoservicios.centros.presentation.screens.residences.list.ResidenceListScreen
import com.altoservicios.centros.presentation.screens.residences.map.ResidencesMapScreen
import com.altoservicios.centros.presentation.screens.residences.search.ResidenceSearchScreen

fun NavGraphBuilder.ResidencesNavGraph(
    navController: NavHostController,
    residencesViewModel: ResidencesViewModel
) {
    navigation(
        route = Graph.RESIDENCES,
        startDestination = HomeScreen.Residences.route,
    ) {
        val residenceArgs = listOf(navArgument("residence") { type = NavType.StringType })

        composable(route = HomeScreen.Residences.route) {
            ResidenceListScreen(navController, residencesViewModel)
        }

        composable(route = CustomerScreen.Map.route) {
            ResidencesMapScreen(navController, residencesViewModel)
        }

        composable(route = ResidencesRoute.SEARCH) {
            ResidenceSearchScreen(navController, residencesViewModel)
        }

        composable(route = ResidencesRoute.DETAIL, arguments = residenceArgs) { entry ->
            entry.arguments?.getString("residence")?.let {
                ResidenceDetailScreen(navController, Residence.fromJson(it))
            }
        }
    }
}
