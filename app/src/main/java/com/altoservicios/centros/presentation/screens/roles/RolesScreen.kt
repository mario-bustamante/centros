package com.altoservicios.centros.presentation.screens.roles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.screens.roles.components.RolesContent

@Composable
fun RolesScreen(navController: NavHostController) {
    Scaffold { paddingValues ->
        RolesContent(paddingValues)
    }
}