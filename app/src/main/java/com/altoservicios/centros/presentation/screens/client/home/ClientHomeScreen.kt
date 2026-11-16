package com.altoservicios.centros.presentation.screens.client.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.graph.ClientNavGraph
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.navigation.screen.PhotosScreen
import com.altoservicios.centros.presentation.navigation.screen.clientMenuRoute
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBar
import com.altoservicios.centros.presentation.screens.profile.info.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClientHomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {

    val profileViewModel: ProfileViewModel = hiltViewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navBackEntry by navController.currentBackStackEntryAsState()
    var selectedResidenceId by rememberSaveable { mutableStateOf<String?>(null) }
    val entryResidenceId = navBackEntry?.arguments?.getString("residenceId")
    val currentResidenceId = entryResidenceId ?: selectedResidenceId

    LaunchedEffect(navBackEntry) {
        if (navBackEntry?.destination?.route == ClientScreen.Residences.route) {
            selectedResidenceId = null
        } else if (!entryResidenceId.isNullOrBlank()) {
            selectedResidenceId = entryResidenceId
        }
    }

    val screens = listOf(
        ClientScreen.Residence,
        ClientScreen.Rooms,
        PhotosScreen,
        ClientScreen.Profile
    )

    MenuTopBar(
        title = "Residencia",
        drawerState = drawerState,
        screens = screens,
        navController = navController,
        actions = {},
        onLogout = { profileViewModel.logout(onLogout) },
        routeForScreen = { screen -> clientMenuRoute(screen, currentResidenceId) },
        preserveNavigationState = false,
        content = { paddingValues ->
            Scaffold(
                Modifier.padding(paddingValues),
                bottomBar = { ClientBottomBar(navController = navController) }
            ) { paddingValues ->
                ClientNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                )
            }
        }
    )
}