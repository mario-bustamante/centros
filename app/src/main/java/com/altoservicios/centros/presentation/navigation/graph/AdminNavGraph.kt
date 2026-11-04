package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.admin.AdminViewModel

@Composable
fun AdminNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val adminViewModel: AdminViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        route = Graph.ADMIN,
        startDestination = Graph.PROFILE,
        modifier = modifier
    ) {

        ProfileNavGraph(navController)
    }
}
