package com.altoservicios.centros.domain.model

import com.google.gson.Gson

data class Rol(
    val id: String,
    val name: String,
    val image: String,
    val route: String,
) {
    fun toJson(): String = Gson().toJson(this);

    companion object {
        fun fromJson(data: String): Rol = Gson().fromJson(data, Rol::class.java)
    }
}