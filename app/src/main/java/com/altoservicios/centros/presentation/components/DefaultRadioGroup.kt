package com.altoservicios.centros.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.ui.draw.clip


@Composable
fun RadioGroupSelector(
    modifier: Modifier? = Modifier,
    options: List<Pair<Int, String>>, // Ajusta el tipo si tu ID es String o Long
    selectedId: Int?,
    label: String,
    onSelected: (Int?) -> Unit
) {
    val finalModifier = (modifier ?: Modifier).fillMaxWidth()

    // selectableGroup() agrupa las opciones para lectores de pantalla (accesibilidad)
    Column(modifier = finalModifier.selectableGroup()) {
        // Título del grupo (reemplaza al label del OutlinedTextField)
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        options.forEach { (id, text) ->
            RadioOptionRow(
                text = text,
                selected = selectedId == id,
                onClick = { onSelected(id) },
            )
        }
    }
}

@Composable
private fun RadioOptionRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Separa las filas blancas entre sí
            .clip(shape) // Recorta el efecto ripple y el fondo a las esquinas redondeadas
            .background(Color.White) // Fondo blanco
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = shape
            ) // Borde sutil similar a tu OutlinedTextField anterior
            .height(48.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null // Se deja en null porque el clic lo maneja toda la fila (.selectable)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}
