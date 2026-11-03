package com.altoservicios.centros.domain.model

/** Opciones y textos del formulario de contacto; cada opcion es (valor, etiqueta). */
data class ValoresFormulario(
    val urgencia: List<Pair<String, String>> = emptyList(),
    val parentesco: List<Pair<String, String>> = emptyList(),
    val trato: List<Pair<String, String>> = emptyList(),
    val textos: Map<String, String> = emptyMap(),
    val codigos: List<Pair<String, String>> = emptyList(),
    val fecha: String? = null
)

data class Contacto(
    val nombre: String? = null,
    val email: String? = null,
    val codigo: String? = null,
    val telefono: String? = null,
    val urgencia: String? = null,
    val presupuesto: Int = 1500,
    val parentesco: String? = null,
    val preferencia: String? = null,
    val observaciones: String? = null
)

data class Persona(
    val trato: String? = null,
    val nombre: String? = null,
    val domicilio: String? = null,
    val edad: String? = null,
    val obs: String? = null
)
