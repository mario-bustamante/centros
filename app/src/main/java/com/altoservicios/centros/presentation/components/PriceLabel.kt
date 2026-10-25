package com.altoservicios.centros.presentation.components

import androidx.compose.runtime.Composable

@Composable
fun priceLabel(price: String?): String =
    if (!price.isNullOrEmpty() && price != "0") "Precios desde: $price €" else "Precios: No disponibles"
