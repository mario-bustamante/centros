package com.altoservicios.centros.presentation.screens.customer.home.components

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.altoservicios.centros.presentation.navigation.screen.CustomerScreen

@Composable
fun CustomerBottomBar(navController: NavHostController) {

    val screens = listOf(
        CustomerScreen.Residences,
        CustomerScreen.Map,
        CustomerScreen.Profile,
    )

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination
    val bottomBarDestination = screens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    if(bottomBarDestination) {
        NavigationBar {
            screens.forEach { screen ->
                CustomerBottomBarItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

}