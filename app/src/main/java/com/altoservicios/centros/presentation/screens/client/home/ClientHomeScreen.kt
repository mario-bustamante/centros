package com.altoservicios.centros.presentation.screens.client.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClientHomeScreen(
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