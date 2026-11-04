package com.altoservicios.centros.presentation.screens.home.contact

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.screens.home.contact.components.HomeContactContent

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeContactScreen(
    navController: NavHostController,
    vm: HomeContactViewModel = hiltViewModel()
) {
    Scaffold { paddingValues ->
        HomeContactContent(vm)
    }
}
