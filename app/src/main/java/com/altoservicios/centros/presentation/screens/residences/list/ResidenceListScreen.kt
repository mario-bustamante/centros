package com.altoservicios.centros.presentation.screens.residences.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.navigation.screen.ResidencesRoute
import com.altoservicios.centros.presentation.screens.residences.ResidencesViewModel
import com.altoservicios.centros.presentation.screens.residences.list.components.ResidenceListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResidenceListScreen(
    navController: NavHostController,
    vm: ResidencesViewModel
) {

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(ResidencesRoute.SEARCH)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            ) { paddingValues ->
                ResidenceListContent(
                    paddingValues = paddingValues,
                    residences = vm.residences,
                    isLoading = vm.isLoading,
                    errorMessage = vm.errorMessage,
                    onErrorConsumed = { vm.errorMessage = "" },
                    onLoadNextPage = vm::loadNextPage,
                    onResidenceClick = { navController.navigate(ResidencesRoute.detail(it)) }
                )
            }

}