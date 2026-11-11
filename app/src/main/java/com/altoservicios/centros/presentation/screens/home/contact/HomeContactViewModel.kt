package com.altoservicios.centros.presentation.screens.home.contact

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

object ContactStep {
    const val DATA = 2
    const val OWN_PERSON = 10
    const val OTHER_PERSONS = 20
}

@HiltViewModel
class HomeContactViewModel @Inject constructor(
    private val contactUseCase: ContactUseCase
) : ViewModel(), PriceUpdater {

    var valores by mutableStateOf(ValoresFormulario())
        private set

    var step by mutableIntStateOf(ContactStep.DATA)
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
        if (contacto.parentesco == "1") {
            activeStep = ContactStep.OWN_PERSON
            personas[0] = personas[0].copy(nombre = contacto.nombre)
        } else {
            activeStep = ContactStep.OTHER_PERSONS
            personas[0] = personas[0].copy(nombre = null)
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
        // TODO: enviar al backend con el token de reCAPTCHA cuando exista la API
        errorMessage = "Integra reCAPTCHA para poder enviar el formulario."
    }

    override fun updatePriceInitial(value: Int) {
        contacto.presupuesto = value
        prices = prices.copy(initial = value)
    }
}
