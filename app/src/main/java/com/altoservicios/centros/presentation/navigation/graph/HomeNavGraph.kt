package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.HomeRoute
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen
import com.altoservicios.centros.presentation.screens.home.HomeResidencesViewModel
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactScreen
import com.altoservicios.centros.presentation.screens.home.detail.HomeResidenceDetailScreen
import com.altoservicios.centros.presentation.screens.home.list.HomeResidenceListScreen
import com.altoservicios.centros.presentation.screens.home.search.HomeResidenceSearchScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val residencesViewModel: HomeResidencesViewModel = hiltViewModel()
    val residenceArgs = listOf(navArgument("residence") { type = NavType.StringType })

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeScreen.Residences.route,
        modifier = modifier
    ) {
        composable(route = HomeScreen.Residences.route) {
            HomeResidenceListScreen(navController, residencesViewModel)
        }

        composable(route = HomeScreen.Contact.route) {
            HomeContactScreen(navController)
        }

        composable(route = HomeRoute.SEARCH) {
            HomeResidenceSearchScreen(navController, residencesViewModel)
        }

        composable(route = HomeRoute.DETAIL, arguments = residenceArgs) { entry ->
            entry.arguments?.getString("residence")?.let {
                HomeResidenceDetailScreen(navController, Residence.fromJson(it))
            }
        }
    }
}
