package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName;

data class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("username") val name: String,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("roles") val roles: List<Rol>? = null,
) {
    fun toJson(): String = Gson().toJson(this);

    companion object {
        fun fromJson(data: String): User = Gson().fromJson(data, User::class.java)
    }
}
