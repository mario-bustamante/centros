package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val user: User,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("access_token") val token: String? = null,
) {
    fun toJson(): String = Gson().toJson(this);

    companion object {
        fun fromJson(data: String): AuthResponse = Gson().fromJson(data, AuthResponse::class.java)
    }
}
