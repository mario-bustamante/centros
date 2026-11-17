package com.altoservicios.centros.presentation.screens.home.contact.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altoservicios.centros.R
import com.altoservicios.centros.presentation.components.DefaultButton
import com.altoservicios.centros.presentation.screens.home.contact.ContactStep
import com.altoservicios.centros.presentation.screens.home.contact.HomeContactViewModel

@Composable
fun HomeContactContent(
    vm: HomeContactViewModel
) {
    val context = LocalContext.current
    val sendWhatsApp: () -> Unit = {
        vm.send()
        val numero = vm.phoneWhatsapp.filter { it in '0'..'9' }
        val mensaje = vm.whatsappMessage

        if (numero.isBlank()) {
            vm.errorMessage = "Falta el número de WhatsApp"
        } else if (mensaje != null) {
            val uri = Uri.Builder()
                .scheme("https")
                .authority("wa.me")
                .appendPath(numero)
                .appendQueryParameter("text", mensaje)
                .build()

            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                vm.errorMessage = ""
            } catch (e: ActivityNotFoundException) {
                vm.errorMessage = "No se pudo abrir WhatsApp"
            }
        }
    }

    Column(
        modifier = Modifier
            .background(colorResource(R.color.colorBgSecondary))
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            Modifier.padding(top = 10.dp, bottom = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_whatsapp),
                contentDescription = "WhatsApp",
                tint = Color.Unspecified, // Mantiene los colores originales del SVG (verde/blanco)
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Contactar por Whatsapp",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        when (vm.step) {
            ContactStep.CONTACT -> ContactContactStep(
                contacto = vm.contacto,
                valores = vm.valores,
                onChange = { vm.contacto = it },
                errorMessage = vm.errorMessage,
                onContinue = { vm.goTo(ContactStep.DATA) },
                vm = vm
            )

            ContactStep.DATA -> ContactDataStep(
                contacto = vm.contacto,
                valores = vm.valores,
                onChange = { vm.contacto = it },
                errorMessage = vm.errorMessage,
                onBack = { vm.goTo(ContactStep.CONTACT) },
                onContinue = vm::onDataContinue,
                vm = vm
            )

            ContactStep.OWN_PERSON -> {
                ContactDataPersonFields(vm.personas[0], vm.valores) { vm.personas[0] = it }
                ContactErrorText(vm.errorMessage)
                ContactNavButtons(
                    onBack = { vm.goTo(ContactStep.DATA) },
                    nextText = "Enviar",
                    nextIcon = Icons.AutoMirrored.Filled.Send,
                    onNext = sendWhatsApp
                )
            }

            ContactStep.OTHER_PERSONS -> {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    vm.personas.indices.forEach { index ->
                        if (vm.person == index) {
                            DefaultButton(
                                modifier = Modifier,
                                text = "Persona ${index + 1}",
                                onClick = { vm.person = index }
                            )
                        } else {
                            OutlinedButton(
                                onClick = { vm.person = index },
                                modifier = Modifier.heightIn(min = 52.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Blue,
                                    disabledContentColor = Color.White.copy(alpha = 0.4f)
                                ),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 1.dp),
                                contentPadding = ButtonDefaults.ContentPadding
                            ) {
                                Text(text = "Persona ${index + 1}")
                            }
                        }
                    }
                }
                ContactDataPersonFields(vm.personas[vm.person], vm.valores) { vm.personas[vm.person] = it }
                ContactErrorText(vm.errorMessage)
                ContactNavButtons(
                    onBack = { vm.goTo(ContactStep.DATA) },
                    nextText = "Enviar",
                    nextIcon = Icons.AutoMirrored.Filled.Send,
                    onNext = sendWhatsApp
                )
            }
        }
    }
}