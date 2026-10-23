package com.altoservicios.centros.domain.model

data class Search(
    val searchFor: String = "",
    val province: Int? = null,
    val town: Int? = null,
    val price: Int? = null,
    val room: Int? = null,
    val sector: Int? = null,
    val dependence: Int? = null,
    val near: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
)
