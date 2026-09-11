package com.altoservicios.centros.presentation.screens.admin.home

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.navigation.graph.AdminNavGraph
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminHomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { ClientBottomBar(navController = navController) }
    ) { paddingValues ->
        AdminNavGraph(navController = navController)
    }
}