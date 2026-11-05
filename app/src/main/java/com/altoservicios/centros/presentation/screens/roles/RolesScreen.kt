package com.altoservicios.centros.presentation.screens.roles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.screens.home.components.HomeTopBar
import com.altoservicios.centros.presentation.screens.roles.components.RolesContent

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RolesScreen(
    navController: NavHostController,
    onLogout: () -> Unit = {}
) {

    HomeTopBar(
        navController = navController,
        content = { paddingValues ->
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { },
                        navigationIcon = {
                            Column(
                                modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_logo_h),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(150.dp)
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                RolesContent(paddingValues, navController, onLogout = onLogout)
            }
        }
    )
}