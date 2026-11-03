package com.altoservicios.centros.presentation.screens.client.rooms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altoservicios.centros.R

@Composable
fun ListRooms(
    title: String,
    items: List<Pair<Int, String>>,
    quantities: Map<Int, String> = emptyMap()
) {
    if (items.isEmpty()) return

    Text(
        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = colorResource(R.color.colorPrimary)
    )
    items.forEach { (id, text) ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                quantities[id]?.let { quantity ->
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .width(110.dp),
                        value = quantity,
                        onValueChange = { },
                        readOnly = true,
                        singleLine = true,
                        label = { Text(text = "Disponibles") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }
        }
    }
}
