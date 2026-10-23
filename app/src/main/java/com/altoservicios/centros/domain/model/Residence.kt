package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Residence(
    @SerializedName("id") val id: String = "",
    @SerializedName("nombre") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("web") val web: String? = null,
    @SerializedName("precio") val price: String? = null,
    @SerializedName("plazas_libres") val sectors: String? = null,
    @SerializedName("telefono") val phone: String? = null,
    @SerializedName("direccion") val address: String? = null,
    @SerializedName("provincia") val province: String? = null,
    @SerializedName("municipio") val town: String? = null,
    @SerializedName("imagen_url") val imageUrl: String? = null,
    @SerializedName("descripcion_resumen") val description: String? = null,
    @SerializedName("latitud") val latitude: String? = null,
    @SerializedName("longitud") val longitude: String? = null,
    @SerializedName("provincia_id") val provinceId: String? = null,
    @SerializedName("municipios_id") val townId: String? = null,
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(data: String): Residence = Gson().fromJson(data, Residence::class.java)
    }
}

data class ResidenceResponse(
    @SerializedName("residencia") val data: Residence? = null,
)

data class ResidencesResponse(
    @SerializedName("data") val data: List<Residence>? = null,
    @SerializedName("current_page") val currentPage: Int? = null,
)

/** Residencias de una pagina y numero de la siguiente pagina (null si es la ultima). */
data class ResidencesPage(
    val nextPage: Int?,
    val residences: List<Residence>,
)
