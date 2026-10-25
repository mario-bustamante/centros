package com.altoservicios.centros.presentation.screens.client.residences.list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.MenuTopBar
import com.altoservicios.centros.presentation.navigation.screen.ClientRoute
import com.altoservicios.centros.presentation.navigation.screen.ClientScreen
import com.altoservicios.centros.presentation.screens.auth.register.RegisterScreen
import com.altoservicios.centros.presentation.screens.client.home.components.ClientBottomBarItem
import com.altoservicios.centros.presentation.screens.client.residences.ClientResidencesViewModel
import com.altoservicios.centros.presentation.screens.client.residences.list.components.ClientResidenceListContent
import kotlinx.coroutines.launch
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientResidenceListScreen(navController: NavHostController, vm: ClientResidencesViewModel) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Buscar Residencias",
                actions = {
                    IconButton(onClick = { navController.navigate(ClientRoute.SEARCH) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        ClientResidenceListContent(
            paddingValues = paddingValues,
            residences = vm.residences,
            isLoading = vm.isLoading,
            errorMessage = vm.errorMessage,
            onErrorConsumed = { vm.errorMessage = "" },
            onLoadNextPage = vm::loadNextPage,
            onResidenceClick = { navController.navigate(ClientRoute.detail(it)) }
        )
    }
}
