package com.altoservicios.centros.presentation.screens.home.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.components.DefaultTopBar
import com.altoservicios.centros.presentation.components.dial
import com.altoservicios.centros.presentation.components.openWhatsApp
import com.altoservicios.centros.presentation.screens.home.contact.components.HomeContactContent


@Composable
fun HomeContactScreen(navController: NavHostController) {

    Scaffold(
      /*  topBar = {
            DefaultTopBar(
                title = "Contacto",
                upAvailable = true,
                navController = navController
            )
        }*/
    ) { paddingValues ->
        HomeContactContent(paddingValues)
    }
}
