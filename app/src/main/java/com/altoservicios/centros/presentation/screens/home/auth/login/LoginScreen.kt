package com.altoservicios.centros.presentation.screens.home.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.screens.home.auth.login.components.Login
import com.altoservicios.centros.presentation.screens.home.auth.login.components.LoginContent

@Composable
fun LoginScreen (navController: NavHostController) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Login",
                upAvailable = true,
                navController = navController
            )
        }
    ) { paddingValues ->
        LoginContent(navController, paddingValues)
    }

    Login(navController = navController)
}