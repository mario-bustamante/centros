package com.altoservicios.centros.presentation.screens.customer.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.graph.CustomerNavGraph
import com.altoservicios.centros.presentation.navigation.screen.CustomerScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CustomerHomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val screens = listOf(
        CustomerScreen.Residences,
        CustomerScreen.Map,
        CustomerScreen.Profile
    )

    MenuTopBar(
        title = "Perfil",
        drawerState = drawerState,
        screens = screens,
        navController = navController,
        actions = {},
        onLogout = { profileViewModel.logout(onLogout) },
        content = { paddingValues ->
            Scaffold(
                // bottomBar = { CustomerBottomBar(navController = navController) }
            ) { paddingValues ->
                CustomerNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                )
            }
        }
    )
}