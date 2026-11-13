package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.altoservicios.centros.domain.model.Persona
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.presentation.components.DefaultDropdown
import com.altoservicios.centros.presentation.components.DefaultTextField

@Composable
fun ContactDataPersonFields(
    persona: Persona,
    valores: ValoresFormulario,
    onChange: (Persona) -> Unit
) {
    val lbText = valores.textos

    DefaultDropdown(
        label = lbText["trato"] ?: "Trato",
        emptyLabel = "Selecciona trato",
        options = valores.trato,
        selectedId = persona.trato,
        onSelected = { onChange(persona.copy(trato = it)) }
    )

    DefaultTextField(
        label = lbText["nombre"] ?: "Nombre",
        value = persona.nombre.orEmpty(),
        onValueChange = { onChange(persona.copy(nombre = it)) }
    )
    DefaultTextField(
        label = lbText["domicilio"] ?: "Domicilio",
        value = persona.domicilio.orEmpty(),
        onValueChange = { onChange(persona.copy(domicilio = it)) }
    )
    DefaultTextField(
        label = lbText["edad"] ?: "Edad",
        value = persona.edad.orEmpty(),
        onValueChange = { onChange(persona.copy(edad = it)) },
        keyboardType = KeyboardType.Number
    )
    DefaultTextField(
        label = lbText["obs"] ?: "Observaciones",
        value = persona.obs.orEmpty(),
        onValueChange = { if (it.length <= 1000) onChange(persona.copy(obs = it)) },
        singleLine = false
    )
}
