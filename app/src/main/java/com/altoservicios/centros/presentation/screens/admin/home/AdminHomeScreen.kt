package com.altoservicios.centros.presentation.screens.admin.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBar


@Composable
fun AdminHomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {
    Scaffold(
        bottomBar = { ClientBottomBar(navController = navController) }
    ) { paddingValues ->
        ClientNavGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}