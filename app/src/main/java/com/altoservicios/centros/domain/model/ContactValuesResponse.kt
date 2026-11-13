package com.altoservicios.centros.domain.model

import com.google.gson.annotations.SerializedName

data class CountryCode(
    @SerializedName("p") val country: String? = null,
    @SerializedName("c") val code: String? = null,
    @SerializedName("b") val flag: String? = null
)

data class ContactValuesResponse(
    @SerializedName("data") val data: ContactValuesData? = null
)

data class ContactValuesData(
    @SerializedName("fecha") val date: String? = null,
    @SerializedName("urgencia") val urgency: Map<Int, String>? = null,
    @SerializedName("parentesco") val relationship: Map<Int, String>? = null,
    @SerializedName("trato") val title: Map<Int, String>? = null,
    @SerializedName("textos") val texts: Map<String, String>? = null,
    @SerializedName("codigos") val codes: List<CountryCode>? = null,
    @SerializedName("precios") val prices: Map<String, String>? = null,
)
