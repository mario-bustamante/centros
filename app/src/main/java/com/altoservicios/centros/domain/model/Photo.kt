package com.altoservicios.centros.domain.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") val id: Int? = null,
    @SerializedName(value = "path", alternate = ["imagen_url"]) val path: String? = null,
    @SerializedName(value = "title", alternate = ["titulo"]) val title: String? = null,
    @SerializedName(value = "description", alternate = ["descripcion"]) val description: String? = null,
    @SerializedName("principal") val principal: Int? = null,
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(data: String): Photo = Gson().fromJson(data, Photo::class.java)
    }
}

data class PhotoResponse(
    @SerializedName("data") val data: List<Photo>? = null,
    @SerializedName("meta") val meta: PhotoMeta? = null,
)

data class PhotoMeta(
    @SerializedName("image_count") val imageCount: Int? = null,
)

data class PhotoUpdateResponse(
    @SerializedName("data") val data: Photo? = null,
)

data class PhotoUploadResponse(
    @SerializedName("data") val data: String? = null,
)

data class PhotoDeleteResponse(
    @SerializedName("data") val data: JsonElement? = null,
)
