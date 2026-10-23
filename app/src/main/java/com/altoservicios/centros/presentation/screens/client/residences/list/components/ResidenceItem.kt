package com.altoservicios.centros.presentation.screens.client.residences.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.altoservicios.centros.domain.model.Residence
import com.altoservicios.centros.presentation.components.imageUrl

@Composable
fun ResidenceItem(residence: Residence, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                modifier = Modifier
                    .size(width = 110.dp, height = 80.dp),
                model = imageUrl(residence.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = residence.name.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.height(20.dp),
                    text = priceLabel(residence.price),
                    fontSize = 13.sp
                )
                Text(
                    text = listOfNotNull(residence.address, residence.town, residence.province)
                        .filter { it.isNotBlank() }
                        .joinToString(", "),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun priceLabel(price: String?): String =
    if (!price.isNullOrEmpty() && price != "0") "Precios desde: $price €" else "Precios: No disponibles"
