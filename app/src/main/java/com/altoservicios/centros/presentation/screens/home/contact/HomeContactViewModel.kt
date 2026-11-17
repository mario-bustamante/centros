package com.altoservicios.centros.presentation.screens.home.contact

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altoservicios.centros.domain.model.Contacto
import com.altoservicios.centros.domain.model.Persona
import com.altoservicios.centros.domain.model.Price
import com.altoservicios.centros.domain.model.ValoresFormulario
import com.altoservicios.centros.domain.useCase.contact.ContactUseCase
import com.altoservicios.centros.domain.util.Resource
import com.altoservicios.centros.presentation.components.PriceUpdater
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

object ContactStep {
    const val CONTACT = 1
    const val DATA = 2
    const val OWN_PERSON = 10
    const val OTHER_PERSONS = 11
}

internal data class ContactSubmission(
    val contacto: Contacto,
    val personas: List<Persona>
) {
    fun toJson(): String = GsonBuilder().serializeNulls().create().toJson(this)

    fun toWhatsAppMessage(valores: ValoresFormulario): String = buildString {
        fun text(value: String?): String = value?.takeIf { it.isNotBlank() } ?: "No indicado"
        fun option(value: Int?, options: List<Pair<Int, String>>): String =
            options.firstOrNull { it.first == value }?.second ?: value?.toString() ?: "No indicado"

        appendLine("Datos de contacto")
        appendLine("Nombre: ${text(contacto.nombre)}")
        appendLine("Email: ${text(contacto.email)}")
        appendLine("Código telefónico: ${text(contacto.codigo)}")
        appendLine("Teléfono: ${text(contacto.telefono)}")
        appendLine("Urgencia: ${option(contacto.urgencia, valores.urgencia)}")
        appendLine("Preferencia: ${text(contacto.preferencia)}")
        appendLine("Parentesco: ${option(contacto.parentesco, valores.parentesco)}")
        appendLine("Presupuesto: ${contacto.presupuesto} €")
        appendLine("Observaciones de contacto: ${text(contacto.observaciones)}")
        personas.forEachIndexed { index, persona ->
            appendLine()
            appendLine("Persona ${index + 1}")
            appendLine("Trato: ${option(persona.trato, valores.trato)}")
            appendLine("Nombre: ${text(persona.nombre)}")
            appendLine("Domicilio: ${text(persona.domicilio)}")
            appendLine("Edad: ${text(persona.edad)}")
            appendLine("Observaciones: ${text(persona.obs)}")
        }
    }.trimEnd()
}

@HiltViewModel
class HomeContactViewModel @Inject constructor(
    private val contactUseCase: ContactUseCase
) : ViewModel(), PriceUpdater {

    var valores by mutableStateOf(ValoresFormulario())
        private set

    var step by mutableIntStateOf(ContactStep.CONTACT)
        private set
    var optionSelected by mutableIntStateOf(0)
    var person by mutableIntStateOf(0)
    var contacto by mutableStateOf(Contacto())
    val personas = mutableStateListOf(Persona(), Persona())
    var prices by mutableStateOf<Price>(
        value = Price()
    )
        private set
    var errorMessage by mutableStateOf("")
    var whatsappMessage: String? = null
        private set
    var jsonPayload: String? = null
        private set

    var phoneWhatsapp by mutableStateOf("123456789")

    private var activeStep = ContactStep.OWN_PERSON
    private var dataVisited = false

    init {
        loadValores()
    }

    private fun loadValores() {
        viewModelScope.launch {
            when (val result = contactUseCase.getValues()) {
                is Resource.Success -> applyValores(result.data)
                is Resource.Failure -> errorMessage = result.message
                Resource.Loading -> Unit
            }
        }
    }

    fun applyValores(newValores: ValoresFormulario) {
        valores = newValores
        prices = newValores.prices
        if (contacto.codigo == null) {
            contacto = contacto.copy(codigo = newValores.codigos.firstOrNull()?.first)
        }
    }

    fun onMotiveContinue() {
        if (optionSelected == 1) {
            errorMessage = ""
            goTo(ContactStep.DATA)
        } else {
            errorMessage = "Selecciona una opción antes de continuar"
        }
    }

    fun onDataContinue() {
        /*
        if (contacto.nombre.isNullOrBlank() || contacto.email.isNullOrBlank() ||
            contacto.telefono.isNullOrBlank() || contacto.parentesco.isNullOrBlank()
        ) {
            errorMessage = "Completa los campos obligatorios"
            return
        }
        */
        errorMessage = ""
        if (contacto.parentesco == 1) {
            if (activeStep != ContactStep.OWN_PERSON || personas[0].nombre == null) {
                personas[0] = personas[0].copy(nombre = contacto.nombre)
            }
            activeStep = ContactStep.OWN_PERSON
        } else {
            if (activeStep != ContactStep.OTHER_PERSONS) {
                personas[0] = personas[0].copy(nombre = null)
            }
            activeStep = ContactStep.OTHER_PERSONS
        }
        goTo(activeStep)
    }

    fun goTo(target: Int) {
        errorMessage = ""
        if (target == ContactStep.DATA) dataVisited = true
        step = target
    }

    fun goToActivePersonStep() {
        if (dataVisited) step = activeStep
    }

    fun send() {
        whatsappMessage = null
        jsonPayload = null
        val selectedPersons = when (step) {
            ContactStep.OWN_PERSON -> personas.take(1)
            ContactStep.OTHER_PERSONS -> personas.toList()
            else -> {
                errorMessage = "Completa los datos de las personas antes de enviar"
                return
            }
        }
        val submission = ContactSubmission(contacto.copy(), selectedPersons.map { it.copy() })
        whatsappMessage = submission.toWhatsAppMessage(valores)
        jsonPayload = submission.toJson()

        Log.d("HomeContactViewModel", whatsappMessage.toString())
        Log.d("HomeContactViewModel", jsonPayload.toString())

        // TODO: enviar al backend con el token de reCAPTCHA cuando exista la API
        //errorMessage = "Integra reCAPTCHA para poder enviar el formulario."










    }

    override fun updatePriceInitial(value: Int) {
        contacto.presupuesto = value
        prices = prices.copy(initial = value)
    }
}
