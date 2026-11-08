package com.altoservicios.centros.presentation.screens.client.residences

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.GenericTopBar
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.client.residences.components.ClientResidencesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientResidencesScreen(
    navController: NavHostController,
    vm: ClientResidencesViewModel
) {

    Scaffold(
        topBar = {
            GenericTopBar()
        }
    ) { paddingValues ->
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