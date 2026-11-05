package com.altoservicios.centros.presentation.screens.roles.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.domain.model.Rol
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.navigation.Graph

@Composable
fun RolesItem(rol: Rol, navController: NavHostController) {

    DefaultButton(
        modifier = Modifier.fillMaxWidth(),
        text = rol.name.uppercase(),
        onClick = {
            navController.navigate(route = rol.route) {
                popUpTo(route = Graph.ROLES) { inclusive = true }
            }
        }
    )
    Spacer(modifier = Modifier.height(25.dp))
}