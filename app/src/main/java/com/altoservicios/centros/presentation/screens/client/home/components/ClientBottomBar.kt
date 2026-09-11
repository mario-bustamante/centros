package com.altoservicios.centros.presentation.screens.client.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.NavigationBar
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hierarchy
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen

@Composable
fun ClientBottomBar(navController: NavHostController) {

    val screens = listOf(
        ClientScreen.ProductList,
        ClientScreen.CategoryList,
        ClientScreen.Profile,
    )

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val bottomBarDestination = screens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    if(bottomBarDestination) {
        NavigationBar {
            screens.forEach { screen ->
                ClientBottomBarItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}