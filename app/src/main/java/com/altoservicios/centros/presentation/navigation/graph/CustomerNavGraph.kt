package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.CustomerScreen
import com.altoservicios.centros.presentation.screens.residences.ResidencesViewModel

@Composable
fun CustomerNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val residencesViewModel: ResidencesViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.CUSTOMER,
        startDestination = Graph.RESIDENCES,
        modifier = modifier
    ) {
        ResidencesNavGraph(navController = navController, residencesViewModel = residencesViewModel)
        ProfileNavGraph(navController)
    }
}
