package com.altoservicios.centros.domain.model

/** Opciones y textos del formulario de contacto; cada opcion es (valor, etiqueta). */
data class ValoresFormulario(
    val urgencia: List<Pair<Int, String>> = emptyList(),
    val parentesco: List<Pair<Int, String>> = emptyList(),
    val trato: List<Pair<Int, String>> = emptyList(),
    val textos: Map<String, String> = emptyMap(),
    val codigos: List<Pair<String, String>> = emptyList(),
    val precios: List<Pair<String, String>> = emptyList(),
    val prices: Price = Price(),
    val fecha: String? = null
)

data class Contacto(
    val nombre: String? = null,
    val email: String? = null,
    val codigo: String? = null,
    val telefono: String? = null,
    val urgencia: Int? = 0,
    var presupuesto: Int = 1500,
    val parentesco: Int? = 0,
    val preferencia: String? = null,
    val observaciones: String? = null
)

data class Persona(
    val trato: Int? = 0,
    val nombre: String? = null,
    val domicilio: String? = null,
    val edad: String? = null,
    val obs: String? = null
)
