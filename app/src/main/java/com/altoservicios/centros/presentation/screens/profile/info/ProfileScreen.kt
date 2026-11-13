package com.altoservicios.centros.presentation.screens.profile.info

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.screens.profile.info.components.ProfileContent

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ProfileScreen(
    navController: NavHostController,
    onEditProfile: () -> Unit
) {
    Scaffold { paddingValues ->
        ProfileContent(onEditProfile, paddingValues = paddingValues)
    }
}
