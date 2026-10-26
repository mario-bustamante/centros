package com.altoservicios.centros.presentation.screens.home.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MenuHomeTopBar
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.navigation.screen.HomeRoute
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen
import com.altoservicios.centros.presentation.screens.home.HomeResidencesViewModel
import com.altoservicios.centros.presentation.screens.home.list.components.HomeResidenceListContent
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeResidenceListScreen(navController: NavHostController, vm: HomeResidencesViewModel) {

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val screens = listOf(
        HomeScreen.Residences,
        HomeScreen.Contact,
        HomeScreen.Auth
    )

    MenuHomeTopBar(
        title = "Residencias",
        drawerState = drawerState,
        screens = screens,
        navController = navController,
        actions = {},
        onLogout = null,
        content = { paddingValues ->

            Scaffold(
                topBar = {
                    DefaultTopBar(
                        title = "Residencias",
                        /*  actions = {
                              IconButton(onClick = { navController.navigate(HomeRoute.SEARCH) }) {
                                  Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                              }
                          }*/
                    )
                },
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
