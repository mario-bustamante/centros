package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName;

data class User(
    @SerializedName("id") val id: Long? = 0,
    @SerializedName("username") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefono") val phone: String,
    @SerializedName("password") val password: String,
) {
    fun toJson(): String = Gson().toJson(this);

    companion object {
        fun fromJson(data: String): User = Gson().fromJson(data, User::class.java)
    }
}
