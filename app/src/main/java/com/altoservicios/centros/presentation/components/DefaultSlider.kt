package com.altoservicios.centros.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altoservicios.centros.R
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
