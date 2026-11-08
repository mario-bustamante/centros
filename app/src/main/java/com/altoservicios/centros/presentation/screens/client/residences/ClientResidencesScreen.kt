package com.altoservicios.centros.presentation.screens.client.residences

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.residences.components.ClientResidencesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientResidencesScreen(
    navController: NavHostController,
    vm: ClientResidencesViewModel
) {

    Scaffold { paddingValues ->
        ClientResidencesContent(
            paddingValues = paddingValues,
            residences = vm.residences,
            isLoading = vm.isLoading,
            errorMessage = vm.errorMessage,
            onErrorConsumed = { vm.errorMessage = "" },
            onLoadNextPage = vm::loadNextPage,
            onResidenceClick = { residence ->
                navController.navigate(ClientScreen.Residence.createRoute(residence.id))
            }
        )
    }
}