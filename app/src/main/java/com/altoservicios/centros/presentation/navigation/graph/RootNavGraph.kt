package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.screens.home.HomeScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(route = Graph.HOME) {
            HomeScreen(rootNavController = navController)
        }
        AuthNavGraph(navController = navController)
        RolesNavGraph(navController = navController)
    }
}