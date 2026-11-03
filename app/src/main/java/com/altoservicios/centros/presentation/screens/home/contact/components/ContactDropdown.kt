package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Lista desplegable de opciones (valor, etiqueta) con valor de tipo texto. */
@Composable
fun ContactDropdown(
    label: String,
    options: List<Pair<String, String>>,
    selected: String?,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = options.firstOrNull { it.first == selected }?.second.orEmpty(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(text = label) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.LightGray,
            ),
            trailingIcon = { Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null) }
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { (value, text) ->
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        expanded = false
                        onSelected(value)
                    }
                )
            }
        }
    }
}
