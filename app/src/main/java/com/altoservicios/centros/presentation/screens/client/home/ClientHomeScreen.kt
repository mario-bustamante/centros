package com.altoservicios.centros.presentation.screens.client.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClientHomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val screens = listOf(
        ClientScreen.Residences,
        ClientScreen.Map,
        ClientScreen.Profile
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
                // bottomBar = { ClientBottomBar(navController = navController) }
            ) { paddingValues ->
                ClientNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                )
            }
        }

    )
}