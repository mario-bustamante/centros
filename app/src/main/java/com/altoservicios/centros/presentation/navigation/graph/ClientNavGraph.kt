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
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel
import com.altoservicios.centros.presentation.screens.client.residences.detail.ClientResidenceDetailScreen
import com.altoservicios.centros.presentation.screens.client.residences.list.ClientResidenceListScreen
import com.altoservicios.centros.presentation.screens.client.residences.map.ClientResidenceMapScreen
import com.altoservicios.centros.presentation.screens.client.residences.map.ClientResidencesMapScreen
import com.altoservicios.centros.presentation.screens.client.residences.search.ClientResidenceSearchScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileScreen
import com.altoservicios.centros.presentation.screens.profile.update.ProfileUpdateScreen

@Composable
fun ClientNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Compartido por listado, buscador y mapa para mantener los mismos filtros
    val residencesViewModel: ClientResidencesViewModel = hiltViewModel()
    val residenceArgs = listOf(navArgument("residence") { type = NavType.StringType })

    NavHost(
        navController = navController,
        route = Graph.CLIENT,
        startDestination = ClientScreen.Residences.route,
        modifier = modifier
    ) {
        composable(route = ClientScreen.Residences.route) {
            ClientResidenceListScreen(navController, residencesViewModel)
        }

        composable(route = ClientScreen.Map.route) {
            ClientResidencesMapScreen(navController, residencesViewModel)
        }

        composable(route = ClientScreen.Profile.route) {
            ProfileScreen(navController = navController, onEditProfile = { navController.navigate(ClientRoute.PROFILE_UPDATE) })
        }

        composable(route = ClientRoute.PROFILE_UPDATE) {
            ProfileUpdateScreen(navController)
        }

        composable(route = ClientRoute.SEARCH) {
            ClientResidenceSearchScreen(navController, residencesViewModel)
        }

        composable(route = ClientRoute.DETAIL, arguments = residenceArgs) { entry ->
            entry.arguments?.getString("residence")?.let {
                ClientResidenceDetailScreen(navController, Residence.fromJson(it))
            }
        }

        composable(route = ClientRoute.RESIDENCE_MAP, arguments = residenceArgs) { entry ->
            entry.arguments?.getString("residence")?.let {
                ClientResidenceMapScreen(navController, Residence.fromJson(it))
            }
        }
    }
}
