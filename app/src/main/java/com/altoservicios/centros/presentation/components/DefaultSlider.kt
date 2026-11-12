package com.altoservicios.centros.presentation.components

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.altoservicios.centros.domain.model.Price
import kotlin.math.roundToInt

@Composable
fun DefaultSlider(
    modifier: Modifier,
    price: Price,
    vm: PriceUpdater
) {
    val min = price.min?.toFloat() ?: 1000F
    val max = price.max?.toFloat() ?: 5000F
    val step = price.step ?: 50

    Slider(
        modifier = modifier,
        value = price.initial?.toFloat() ?: min,
        onValueChange = { newValue ->
            val value = ((newValue / step).roundToInt() * step)
            vm.updatePriceInitial(value)
        },
        valueRange = min..max,
        steps = (((max - min) / step).toInt() - 1).coerceAtLeast(0)
    )
}
