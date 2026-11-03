package com.altoservicios.centros.presentation.screens.home.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.MenuHomeTopBar
import com.altoservicios.centros.presentation.navigation.screen.HomeRoute
import com.altoservicios.centros.presentation.screens.home.HomeResidencesViewModel
import com.altoservicios.centros.presentation.screens.home.list.components.HomeResidenceListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeResidenceListScreen(
    navController: NavHostController,
    vm: HomeResidencesViewModel
) {
    MenuHomeTopBar(
        title = "Residencias",
        navController = navController,
        actions = {},
        onLogout = null,
        content = { paddingValues ->
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(HomeRoute.SEARCH)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            ) { paddingValues ->
                HomeResidenceListContent(
                    paddingValues = paddingValues,
                    residences = vm.residences,
                    isLoading = vm.isLoading,
                    errorMessage = vm.errorMessage,
                    onErrorConsumed = { vm.errorMessage = "" },
                    onLoadNextPage = vm::loadNextPage,
                    onResidenceClick = { navController.navigate(HomeRoute.detail(it)) }
                )
            }
        }
    )
}