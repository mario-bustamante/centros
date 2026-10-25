package com.altoservicios.centros.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.graph.HomeNavGraph
import com.altoservicios.centros.presentation.navigation.screen.HomeScreen as HomeDestination

@Composable
fun HomeScreen(
    rootNavController: NavHostController,
    navController: NavHostController = rememberNavController()
) {
    val screens = listOf(
        HomeDestination.Residences,
        HomeDestination.Contact,
        HomeDestination.Auth
    )

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val showBottomBar = screens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            selected = screen != HomeDestination.Auth &&
                                currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
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
                            },
                            icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                            label = { Text(text = screen.title) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}
