package com.altoservicios.centros.presentation.screens.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.screens.auth.login.components.Login
import com.altoservicios.centros.presentation.screens.auth.login.components.LoginContent

@Composable
fun LoginScreen(
    navController: NavHostController,
    rootNavController: NavHostController
) {
    Scaffold { paddingValues ->
        LoginContent(navController, paddingValues)
    }

    Login(navController = navController, rootNavController = rootNavController)
}