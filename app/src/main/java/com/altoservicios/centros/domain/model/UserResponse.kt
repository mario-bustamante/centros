package com.altoservicios.centros.domain.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data") val data: User? = null,
)
