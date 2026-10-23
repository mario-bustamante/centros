package com.altoservicios.centros.presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

/** Muestra [message] como Toast cada vez que cambia y no esta vacio. */
@Composable
fun MessageEffect(message: String, onConsumed: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            onConsumed()
        }
    }
}
