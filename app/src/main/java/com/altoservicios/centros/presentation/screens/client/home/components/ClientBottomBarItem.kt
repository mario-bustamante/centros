package com.altoservicios.centros.presentation.screens.client.home.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.navigation.screen.MenuScreen
import com.altoservicios.centros.presentation.navigation.screen.PhotosScreen

@Composable
fun RowScope.ClientBottomBarItem(
    screen: MenuScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    currentResidenceId: String? = null
) {
    val routeToNavigate = when {
        screen is PhotosScreen && currentResidenceId != null -> {
            PhotosScreen.createRoute(currentResidenceId)
        }
        screen == ClientScreen.Rooms && currentResidenceId != null -> {
            ClientScreen.Rooms.createRoute(currentResidenceId)
        }
        else -> screen.route
    }
    
    val isSelected = when {
        screen is PhotosScreen -> {
            currentDestination?.hierarchy?.any {
                it.route?.startsWith("client/photos") == true
            } == true
        }
        screen == ClientScreen.Rooms -> {
            currentDestination?.hierarchy?.any {
                it.route?.startsWith("client/rooms") == true
            } == true
        }
        else -> {
            currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true
        }
    }
    
    NavigationBarItem(
        selected = isSelected,
        onClick = {
            navController.navigate(routeToNavigate) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = null)
        }
    )
}