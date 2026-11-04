package com.altoservicios.centros.presentation.screens.home.contact.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.screens.home.contact.ContactStep
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactViewModel

@Composable
fun HomeContactContent(
    vm: HomeContactViewModel
) {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.colorBgSecondary))
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        when (vm.step) {
            ContactStep.DATA -> ContactDataStep(
                contacto = vm.contacto,
                valores = vm.valores,
                onChange = { vm.contacto = it },
                errorMessage = vm.errorMessage,
                onBack = { },
                onContinue = vm::onDataContinue
            )

            ContactStep.OWN_PERSON -> {
                ContactDataPersonFields(vm.personas[0], vm.valores) { vm.personas[0] = it }
                ContactErrorText(vm.errorMessage)
                ContactNavButtons(
                    onBack = { vm.goTo(ContactStep.DATA) },
                    nextText = "Enviar",
                    onNext = vm::send
                )
            }

            ContactStep.OTHER_PERSONS -> {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    vm.personas.indices.forEach { index ->
                        if (vm.person == index) {
                            DefaultButton(modifier = Modifier, text = "Persona ${index + 1}", onClick = { vm.person = index })
                        } else {
                            OutlinedButton(onClick = { vm.person = index }) { Text(text = "Persona ${index + 1}") }
                        }
                    }
                }
                ContactDataPersonFields(vm.personas[vm.person], vm.valores) { vm.personas[vm.person] = it }
                ContactErrorText(vm.errorMessage)
                ContactNavButtons(
                    onBack = { vm.goTo(ContactStep.DATA) },
                    nextText = "Enviar",
                    onNext = vm::send
                )
            }
        }
    }
}
