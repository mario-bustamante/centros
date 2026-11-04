package com.altoservicios.centros.presentation.screens.roles.components
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.screens.roles.RolesViewModel

@Composable
fun RolesContent(paddingValues: PaddingValues, navController: NavHostController, vm: RolesViewModel = hiltViewModel()) {
    val roles = vm.authResponse.user?.roles.orEmpty()

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            vm.isLoading -> CircularProgressIndicator()
            roles.isEmpty() -> Text(text = "No hay roles disponibles para esta cuenta.")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = roles) { rol ->
                    RolesItem(rol = rol, navController = navController)
                }
            }
        }
    }
}