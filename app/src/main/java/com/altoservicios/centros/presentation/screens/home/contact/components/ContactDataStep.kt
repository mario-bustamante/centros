package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.FormTextField

@Composable
fun ContactDataStep(
    contacto: Contacto,
    valores: ValoresFormulario,
    onChange: (Contacto) -> Unit,
    errorMessage: String,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val textos = valores.textos

    FormTextField(
        label = textos["nombre"] ?: "Nombre",
        value = contacto.nombre.orEmpty(),
        onValueChange = { onChange(contacto.copy(nombre = it)) }
    )
    FormTextField(
        label = textos["correo"] ?: "Email",
        value = contacto.email.orEmpty(),
        onValueChange = { onChange(contacto.copy(email = it)) },
        keyboardType = KeyboardType.Email
    )
    FormTextField(
        label = textos["telefono"] ?: "Teléfono",
        value = contacto.telefono.orEmpty(),
        onValueChange = { onChange(contacto.copy(telefono = it)) },
        keyboardType = KeyboardType.Phone
    )
    FormTextField(
        label = textos["preferencia"] ?: "Preferencia",
        value = contacto.preferencia.orEmpty(),
        onValueChange = { onChange(contacto.copy(preferencia = it)) }
    )
    ContactDropdown(
        label = textos["urgencia"] ?: "Urgencia",
        options = valores.urgencia,
        selected = contacto.urgencia,
        onSelected = { onChange(contacto.copy(urgencia = it)) }
    )
    Text(text = "Presupuesto: ${contacto.presupuesto}€")
    Slider(
        value = contacto.presupuesto.toFloat(),
        onValueChange = { onChange(contacto.copy(presupuesto = it.toInt())) },
        valueRange = 1000f..5000f,
        steps = 399
    )
    ContactDropdown(
        label = textos["parentesco"] ?: "Parentesco",
        options = valores.parentesco,
        selected = contacto.parentesco,
        onSelected = { onChange(contacto.copy(parentesco = it)) }
    )
    ContactErrorText(errorMessage)
    ContactNavButtons(nextText = "Continuar", onNext = onContinue)
}
