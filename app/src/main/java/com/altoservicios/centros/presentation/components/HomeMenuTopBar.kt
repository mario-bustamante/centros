package com.altoservicios.centros.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.altoservicios.centros.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuHomeTopBar(
    title: String,
    navController: NavHostController,
    actions: @Composable RowScope.() -> Unit = {},
    onLogout: (() -> Unit)? = null,
    showTopBar: Boolean = true,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            if (showTopBar) {
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
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}