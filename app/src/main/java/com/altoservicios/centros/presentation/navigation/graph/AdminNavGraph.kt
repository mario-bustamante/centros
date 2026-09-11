package com.altoservicios.centros.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.altoservicios.centros.presentation.navigation.Graph
import com.altoservicios.centros.presentation.navigation.screen.AdminScreen
import com.altoservicios.centros.presentation.screens.admin.category.list.AdminCategoryListScreen
import com.altoservicios.centros.presentation.screens.admin.product.list.AdminProductListScreen
import com.altoservicios.centros.presentation.screens.profile.ProfileScreen

@Composable
fun AdminNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ADMIN,
        startDestination = AdminScreen.ProductList.route
    ) {
        composable(route = AdminScreen.ProductList.route) {
           AdminProductListScreen()
        }

        composable(route = AdminScreen.CategoryList.route) {
            AdminCategoryListScreen()
        }

        composable(route = AdminScreen.Profile.route) {
            ProfileScreen()
        }
    }
}