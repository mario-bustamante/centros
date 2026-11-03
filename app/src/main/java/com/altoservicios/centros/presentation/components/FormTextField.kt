package com.altoservicios.centros.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = singleLine,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
