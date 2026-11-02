package com.altoservicios.centros.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.HomeBottomBar
import com.altoservicios.centros.presentation.components.homeBottomBarScreens
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.graph.HomeNavGraph
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen as HomeDestination

@Composable
fun HomeScreen(
    rootNavController: NavHostController,
    navController: NavHostController = rememberNavController()
) {
    val screens = homeBottomBarScreens

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val showBottomBar = screens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                HomeBottomBar(
                    selectedRoute = screens.firstOrNull { screen ->
                        screen != HomeDestination.Auth &&
                            currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    }?.route,
                    onItemClick = { screen ->
                        if (screen == HomeDestination.Auth) {
                            rootNavController.navigate(Graph.AUTH)
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
        }
    ) { paddingValues ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}
