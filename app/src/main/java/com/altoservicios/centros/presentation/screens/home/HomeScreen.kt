package com.altoservicios.centros.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.navigation.graph.HomeNavGraph
import com.altoservicios.centros.presentation.screens.home.components.HomeTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    rootNavController: NavHostController,
    navController: NavHostController = rememberNavController()
) {

    HomeTopBar(
        navController = navController,
        content = { paddingValues ->
            HomeNavGraph(
                navController = navController,
                rootNavController = rootNavController,
                Modifier.padding(paddingValues)
            )
        }
    )
}