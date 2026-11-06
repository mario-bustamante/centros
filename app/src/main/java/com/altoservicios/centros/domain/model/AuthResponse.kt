package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val user: User? = null ,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    // login/google devuelven "token" y refresh devuelve "access_token"
    @SerializedName(value = "access_token", alternate = ["token"]) val token: String? = null,
) {
    // refresh solo devuelve el token, se conserva el usuario de la sesion actual
    fun withRefreshedToken(refreshed: AuthResponse): AuthResponse = copy(
        token = refreshed.token,
        tokenType = refreshed.tokenType ?: tokenType,
        expiresIn = refreshed.expiresIn ?: expiresIn,
    )

    fun toJson(): String = Gson().toJson(this);

    companion object {
        fun fromJson(data: String): AuthResponse = Gson().fromJson(data, AuthResponse::class.java)
    }
}
