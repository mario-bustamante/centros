package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.altoservicios.centros.domain.model.Persona
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.FormTextField

@Composable
fun ContactDataPersonFields(
    persona: Persona,
    valores: ValoresFormulario,
    onChange: (Persona) -> Unit
) {
    val textos = valores.textos

    ContactDropdown(
        label = textos["trato"] ?: "Trato",
        options = valores.trato,
        selected = persona.trato,
        onSelected = { onChange(persona.copy(trato = it)) }
    )
    FormTextField(
        label = textos["nombre"] ?: "Nombre",
        value = persona.nombre.orEmpty(),
        onValueChange = { onChange(persona.copy(nombre = it)) }
    )
    FormTextField(
        label = textos["domicilio"] ?: "Domicilio",
        value = persona.domicilio.orEmpty(),
        onValueChange = { onChange(persona.copy(domicilio = it)) }
    )
    FormTextField(
        label = textos["edad"] ?: "Edad",
        value = persona.edad.orEmpty(),
        onValueChange = { onChange(persona.copy(edad = it)) },
        keyboardType = KeyboardType.Number
    )
    FormTextField(
        label = textos["obs"] ?: "Observaciones",
        value = persona.obs.orEmpty(),
        onValueChange = { if (it.length <= 1000) onChange(persona.copy(obs = it)) },
        singleLine = false
    )
}
