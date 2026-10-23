package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("path") val path: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("principal") val principal: Int? = null,
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(data: String): Photo = Gson().fromJson(data, Photo::class.java)
    }
}

data class PhotoResponse(
    @SerializedName("data") val data: List<Photo>? = null,
    @SerializedName("image_count") val imageCount: Int? = null,
)
