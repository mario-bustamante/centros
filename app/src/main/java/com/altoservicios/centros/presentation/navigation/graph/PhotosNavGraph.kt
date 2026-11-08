package com.altoservicios.centros.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.PhotosScreen
import com.altoservicios.centros.presentation.screens.client.photo.ClientPhotoScreen
import com.altoservicios.centros.presentation.screens.client.photo.ClientPhotosScreen

fun NavGraphBuilder.PhotosNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PHOTOS,
        startDestination = PhotosScreen.route
    ) {

        composable(
            route = PhotosScreen.route,
            arguments = listOf(
                navArgument("residenceId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            ClientPhotosScreen(navController)
        }

        composable(
            route = PhotosScreen.PHOTO,
            arguments = listOf(
                navArgument("photo") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            ClientPhotoScreen(navController)
        }
    }
}