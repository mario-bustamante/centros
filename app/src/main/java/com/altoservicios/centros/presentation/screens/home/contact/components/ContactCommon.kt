package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.altoservicios.centros.presentation.components.DefaultButton

@Composable
fun ContactErrorText(message: String) {
    if (message.isNotBlank()) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun ContactNavButtons(
    onBack: (() -> Unit)? = null,
    nextText: String,
    onNext: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        if (onBack != null) {
            DefaultButton(modifier = Modifier, text = "Volver", color = Color.Gray, onClick = onBack)
        }
        DefaultButton(modifier = Modifier, text = nextText, onClick = onNext)
    }
}
