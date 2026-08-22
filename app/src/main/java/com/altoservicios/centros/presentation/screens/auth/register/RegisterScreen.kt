package com.altoservicios.centros.presentation.screens.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.screens.auth.login.components.LoginContent

@Composable
fun RegisterScreen (navController: NavHostController) {
    Surface() {
        Column() {
            Text("Register")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RegisterScreen(rememberNavController())
}