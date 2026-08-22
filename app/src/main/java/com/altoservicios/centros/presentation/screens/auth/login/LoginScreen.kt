package com.altoservicios.centros.presentation.screens.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.screens.auth.login.components.LoginContent

@Composable
fun LoginScreen (navController: NavHostController) {
    Scaffold() { paddingValues ->
        LoginContent(navController, paddingValues)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Scaffold() { paddingValues ->
        LoginContent(rememberNavController(), paddingValues)
    }
}