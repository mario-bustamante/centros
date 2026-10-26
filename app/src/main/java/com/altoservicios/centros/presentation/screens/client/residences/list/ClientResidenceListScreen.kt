package com.altoservicios.centros.presentation.screens.client.residences.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel
import com.altoservicios.centros.presentation.screens.client.residences.list.components.ClientResidenceListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientResidenceListScreen(navController: NavHostController, vm: ClientResidencesViewModel) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Buscar Residencias",
                actions = {
                    IconButton(onClick = { navController.navigate(ClientRoute.SEARCH) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        ClientResidenceListContent(
            paddingValues = paddingValues,
            residences = vm.residences,
            isLoading = vm.isLoading,
            errorMessage = vm.errorMessage,
            onErrorConsumed = { vm.errorMessage = "" },
            onLoadNextPage = vm::loadNextPage,
            onResidenceClick = { navController.navigate(ClientRoute.detail(it)) }
        )
    }
}
