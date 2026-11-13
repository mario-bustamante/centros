package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.altoservicios.centros.R
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
    Spacer(modifier = Modifier.height(6.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        if (onBack != null) {
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = "Volver",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = onBack
            )
            Spacer(modifier = Modifier.width(12.dp))
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = "Enviar",
                icon = Icons.AutoMirrored.Filled.Send,
                onClick = onBack
            )
        } else {
            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                text = nextText,
                icon = Icons.AutoMirrored.Filled.ArrowForward,
                showIconRight = true,
                onClick = onNext
            )
        }
    }
}


