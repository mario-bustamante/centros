package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.DefaultDropdown
import com.altoservicios.centros.presentation.components.DefaultSlider
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.components.RadioGroupSelector
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactViewModel

@Composable
fun ContactContactStep(
    contacto: Contacto,
    valores: ValoresFormulario,
    onChange: (Contacto) -> Unit,
    errorMessage: String,
    onContinue: () -> Unit,
    vm: HomeContactViewModel
) {
    val lbText = valores.textos

    DefaultTextField(
        label = lbText["nombre"] ?: "Nombre",
        value = contacto.nombre.orEmpty(),
        onValueChange = { onChange(contacto.copy(nombre = it)) }
    )

    DefaultTextField(
        label = lbText["correo"] ?: "Email",
        value = contacto.email.orEmpty(),
        onValueChange = { onChange(contacto.copy(email = it)) },
        keyboardType = KeyboardType.Email
    )

    DefaultTextField(
        label = lbText["telefono"] ?: "Teléfono",
        value = contacto.telefono.orEmpty(),
        onValueChange = { onChange(contacto.copy(telefono = it)) },
        keyboardType = KeyboardType.Phone
    )

    RadioGroupSelector(
        label = lbText["urgencia"] ?: "Urgencia",
        options = valores.urgencia,
        selectedId = contacto.urgencia,
        onSelected = {
            onChange(contacto.copy(urgencia = it))
        }
    )

    ContactErrorText(errorMessage)

    ContactNavButtons(nextText = "Continuar", onNext = onContinue)
}
