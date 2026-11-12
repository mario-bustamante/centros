package com.altoservicios.centros.presentation.screens.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.altoservicios.centros.presentation.components.GenericTopBar
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.AuthScreen
import com.altoservicios.centros.presentation.navigation.screen.ResidencesRoute
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen as HomeDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val screens = homeBottomBarScreens

    val currentScreen = screens.firstOrNull { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    if (currentScreen != null) {

        val isRegisterDestination = currentDestination?.hierarchy?.any {
            it.route == AuthScreen.Register.route
        } == true
        val isResidenceSearchDestination = currentDestination?.hierarchy?.any {
            it.route == ResidencesRoute.SEARCH
        } == true
        val hideHomeBars = isRegisterDestination || isResidenceSearchDestination
        val selectedHomeRoute = when {
            currentDestination?.hierarchy?.any { it.route == Graph.AUTH } == true ->
                HomeDestination.Auth.route
            else -> screens.firstOrNull { screen ->
                screen != HomeDestination.Auth &&
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
            }?.route
        }

        val showTopBar = !hideHomeBars
        Scaffold(
            bottomBar = {
                if (selectedHomeRoute != null && !hideHomeBars) {
                    HomeBottomBar(
                        selectedRoute = selectedHomeRoute,
                        onItemClick = { screen ->
                            if (screen == HomeDestination.Auth) {
                                navController.navigate(Graph.AUTH) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            } else {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            },
            topBar = {
                if (showTopBar) {
                    GenericTopBar()
                }
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    } else {
        content(PaddingValues())
    }
}