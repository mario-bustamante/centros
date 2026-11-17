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
import com.altoservicios.centros.presentation.components.RadioGroupSelector
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

    DefaultTextField(
        label = lbText["preferencia"] ?: "Preferencia",
        value = contacto.preferencia.orEmpty(),
        singleLine = false,
        onValueChange = { onChange(contacto.copy(preferencia = it)) }
    )

    RadioGroupSelector(
        label = lbText["parentesco"] ?: "Parentesco",
        options = valores.parentesco,
        selectedId = contacto.parentesco,
        onSelected = { 
            onChange(contacto.copy(parentesco = it))
        }
    )

    Text(text = "Presupuesto: ${contacto.presupuesto}€")
    DefaultSlider(modifier = Modifier, price = vm.prices, vm = vm)

    ContactErrorText(errorMessage)

    ContactNavButtons(onBack = onBack, nextText = "Continuar", onNext = onContinue)
}
