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
import com.altoservicios.centros.presentation.navigation.screen.CustomerRoute
import com.altoservicios.centros.presentation.navigation.screen.CustomerScreen
import com.altoservicios.centros.presentation.screens.customer.residences.CustomerResidencesViewModel
import com.altoservicios.centros.presentation.screens.customer.residences.detail.CustomerResidenceDetailScreen
import com.altoservicios.centros.presentation.screens.customer.residences.list.CustomerResidenceListScreen
import com.altoservicios.centros.presentation.screens.customer.residences.map.CustomerResidencesMapScreen
import com.altoservicios.centros.presentation.screens.customer.residences.search.CustomerResidenceSearchScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun CustomerNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Compartido por listado, buscador y mapa para mantener los mismos filtros
    val residencesViewModel: CustomerResidencesViewModel = hiltViewModel()
    val residenceArgs = listOf(navArgument("residence") { type = NavType.StringType })

    NavHost(
        navController = navController,
        route = Graph.CUSTOMER,
        startDestination = CustomerScreen.Residences.route,
        modifier = modifier
    ) {
        composable(route = CustomerScreen.Residences.route) {
            CustomerResidenceListScreen(navController, residencesViewModel)
        }

        composable(route = CustomerScreen.Map.route) {
            CustomerResidencesMapScreen(navController, residencesViewModel)
        }

        composable(route = CustomerScreen.Profile.route) {
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(CustomerRoute.PROFILE_UPDATE) })
        }

        composable(route = CustomerRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }

        composable(route = CustomerRoute.SEARCH) {
            CustomerResidenceSearchScreen(navController, residencesViewModel)
        }

        composable(route = CustomerRoute.DETAIL, arguments = residenceArgs) { entry ->
            entry.arguments?.getString("residence")?.let {
                CustomerResidenceDetailScreen(navController, Residence.fromJson(it))
            }
        }

    }
}
