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
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackEntry?.destination


    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                title = {
                  /*  Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_h), // Cambia por tu R.drawable.tu_imagen
                            contentDescription = "Logo de la app",
                            //
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        )
                    }*/
                },

                navigationIcon = {
                    Column(
                        modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_h), // Cambia por tu R.drawable.tu_imagen
                            contentDescription = "Logo",
                            modifier = Modifier

                                //.padding(start = 12.dp, end = 8.dp) // Ajusta el espaciado con el borde y el texto
                                // .fillMaxSize()
                                //.padding(20.dp)
                            .size(150.dp)
                        )

                    }

                }
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }



    /*
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Mi Menú",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()

                screens.forEach { screen ->
                    NavigationDrawerItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        label = {
                            Text(text = screen.title)
                        },
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = null)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                if (onLogout != null) {
                    HorizontalDivider()
                    NavigationDrawerItem(
                        selected = false,
                        onClick = {
                            onLogout()
                            scope.launch { drawerState.close() }
                        },
                        label = { Text("Salir") },
                        icon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                //  }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú"
                            )
                        }
                    },
                    actions = actions
                )
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
    */
}