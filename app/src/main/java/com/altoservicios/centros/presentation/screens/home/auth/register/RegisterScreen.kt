package com.altoservicios.centros.presentation.screens.home.auth.register


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.screens.home.auth.register.components.Register
import com.altoservicios.centros.presentation.screens.home.auth.register.components.RegisterContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen (navController: NavHostController) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Registro",
                upAvailable = true,
                navController
            )
        }

    ) { paddingValues ->
        RegisterContent(navController, paddingValues)
    }
    Register(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RegisterScreen(rememberNavController())
}