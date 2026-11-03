package com.altoservicios.centros.presentation.screens.customer.residences.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.navigation.screen.CustomerRoute
import com.altoservicios.centros.presentation.screens.customer.residences.CustomerResidencesViewModel
import com.altoservicios.centros.presentation.screens.customer.residences.list.components.CustomerResidenceListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerResidenceListScreen(navController: NavHostController, vm: CustomerResidencesViewModel) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Buscar Residencias",
                actions = {
                    IconButton(onClick = { navController.navigate(CustomerRoute.SEARCH) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        CustomerResidenceListContent(
            paddingValues = paddingValues,
            residences = vm.residences,
            isLoading = vm.isLoading,
            errorMessage = vm.errorMessage,
            onErrorConsumed = { vm.errorMessage = "" },
            onLoadNextPage = vm::loadNextPage,
            onResidenceClick = { navController.navigate(CustomerRoute.detail(it)) }
        )
    }
}
