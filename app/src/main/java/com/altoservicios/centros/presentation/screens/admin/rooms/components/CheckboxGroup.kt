package com.altoservicios.centros.presentation.screens.admin.rooms.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.FormTextField

@Composable
fun CheckboxGroup(
    title: String,
    items: List<Pair<Int, String>>,
    selected: Set<Int>,
    onToggle: (Int) -> Unit,
    quantities: Map<Int, String> = emptyMap()
) {
    if (items.isEmpty()) return

    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = title,
        fontWeight = FontWeight.Bold,
        color = colorResource(R.color.colorPrimary)
    )
    items.forEach { (id, text) ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Checkbox(checked = id in selected, onCheckedChange = { onToggle(id) })
            Text(text = text)

            quantities[id]?.let { quantity ->
                FormTextField(
                    label = "N° Disponibles",
                    value = quantity,
                    onValueChange = { },
                    keyboardType = KeyboardType.Number
                )
            }

        }
    }
}
