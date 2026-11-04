package com.altoservicios.centros.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.HomeBottomBar
import com.altoservicios.centros.presentation.components.MenuHomeTopBar
import com.altoservicios.centros.presentation.components.homeBottomBarScreens
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.graph.HomeNavGraph
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen as HomeDestination

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    rootNavController: NavHostController,
    navController: NavHostController = rememberNavController()
) {
    val screens = homeBottomBarScreens

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val selectedHomeRoute = when {
        currentDestination?.hierarchy?.any { it.route == Graph.AUTH } == true ->
            HomeDestination.Auth.route
        else -> screens.firstOrNull { screen ->
            screen != HomeDestination.Auth &&
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
        }?.route
    }

    MenuHomeTopBar(
        title = "",
        navController = navController,
        actions = {},
        onLogout = null,
        bottomBar = {
            if (selectedHomeRoute != null) {
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
        content = { paddingValues ->
            HomeNavGraph(
                navController = navController,
                rootNavController = rootNavController,
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
    )
}
