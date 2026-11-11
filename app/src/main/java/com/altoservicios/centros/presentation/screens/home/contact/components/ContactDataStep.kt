package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.DefaultSlider
import com.altoservicios.centros.presentation.components.FormTextField
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactViewModel

@Composable
fun ContactDataStep(
    contacto: Contacto,
    valores: ValoresFormulario,
    onChange: (Contacto) -> Unit,
    errorMessage: String,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    vm: HomeContactViewModel
) {
    val lbText = valores.textos

    FormTextField(
        label = lbText["nombre"] ?: "Nombre",
        value = contacto.nombre.orEmpty(),
        onValueChange = { onChange(contacto.copy(nombre = it)) }
    )
    FormTextField(
        label = lbText["correo"] ?: "Email",
        value = contacto.email.orEmpty(),
        onValueChange = { onChange(contacto.copy(email = it)) },
        keyboardType = KeyboardType.Email
    )
    FormTextField(
        label = lbText["telefono"] ?: "Teléfono",
        value = contacto.telefono.orEmpty(),
        onValueChange = { onChange(contacto.copy(telefono = it)) },
        keyboardType = KeyboardType.Phone
    )
    FormTextField(
        label = lbText["preferencia"] ?: "Preferencia",
        value = contacto.preferencia.orEmpty(),
        onValueChange = { onChange(contacto.copy(preferencia = it)) }
    )
    ContactDropdown(
        label = lbText["urgencia"] ?: "Urgencia",
        options = valores.urgencia,
        selected = contacto.urgencia,
        onSelected = { onChange(contacto.copy(urgencia = it)) }
    )

    Text(text = "Presupuesto: ${contacto.presupuesto}€")
    DefaultSlider(modifier = Modifier, price = vm.prices, vm = vm)

    ContactDropdown(
        label = lbText["parentesco"] ?: "Parentesco",
        options = valores.parentesco,
        selected = contacto.parentesco,
        onSelected = { onChange(contacto.copy(parentesco = it)) }
    )
    ContactErrorText(errorMessage)
    ContactNavButtons(nextText = "Continuar", onNext = onContinue)
}
