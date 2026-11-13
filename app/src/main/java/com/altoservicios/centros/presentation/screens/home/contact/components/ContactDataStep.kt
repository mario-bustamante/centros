package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.altoservicios.centros.R
import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.DefaultDropdown
import com.altoservicios.centros.presentation.components.DefaultSlider
import com.altoservicios.centros.presentation.components.DefaultTextField
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactViewModel

@Composable
fun ContactDataStep(
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

    DefaultTextField(
        label = lbText["preferencia"] ?: "Preferencia",
        value = contacto.preferencia.orEmpty(),
        onValueChange = { onChange(contacto.copy(preferencia = it)) }
    )

    DefaultDropdown(
        label = lbText["urgencia"] ?: "Urgencia",
        emptyLabel = "Selecciona urgencia",
        options = valores.urgencia,
        selectedId = contacto.urgencia,
        onSelected = {
            onChange(contacto.copy(urgencia = it))
        }
    )

    DefaultDropdown(
        label = lbText["parentesco"] ?: "Parentesco",
        emptyLabel = "Selecciona parentesco",
        options = valores.parentesco,
        selectedId = contacto.parentesco,
        onSelected = { 
            onChange(contacto.copy(parentesco = it))
        }
    )

    Text(text = "Presupuesto: ${contacto.presupuesto}€")
    DefaultSlider(modifier = Modifier, price = vm.prices, vm = vm)

    ContactErrorText(errorMessage)

    ContactNavButtons(nextText = "Continuar", onNext = onContinue)
}
