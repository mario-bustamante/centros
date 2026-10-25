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

// TODO: sustituir por el telefono real de contacto
private const val CONTACT_PHONE = "000000000"

@Composable
fun HomeContactScreen(navController: NavHostController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Contacto",
                upAvailable = true,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "¿Necesitas ayuda para encontrar una residencia?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
                text = "Ponte en contacto con nosotros y te asesoraremos.",
                fontSize = 16.sp
            )
            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Llamar",
                onClick = { context.dial(CONTACT_PHONE) }
            )
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = "WhatsApp",
                color = Color(0xFF25D366),
                onClick = { context.openWhatsApp(CONTACT_PHONE) }
            )
        }
    }
}
