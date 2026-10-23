package com.altoservicios.centros.domain.model

import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("id") val id: Int,
    @SerializedName("provincia") val province: String? = null,
)

data class ProvinceResponse(@SerializedName("data") val data: List<Province>? = null)

data class Town(
    @SerializedName("id") val id: Int,
    @SerializedName("municipio") val town: String? = null,
)

data class TownResponse(@SerializedName("data") val data: List<Town>? = null)

data class Room(
    @SerializedName("id") val id: Int,
    @SerializedName("habitacion") val room: String? = null,
    @SerializedName("residencias_id") val residenceId: Int? = null,
)

data class RoomResponse(@SerializedName("data") val data: List<Room>? = null)

data class Sector(
    @SerializedName("id") val id: Int,
    @SerializedName("plaza") val sector: String? = null,
    @SerializedName("residencias_id") val residenceId: Int? = null,
)

data class SectorResponse(@SerializedName("data") val data: List<Sector>? = null)

data class Dependence(
    @SerializedName("id") val id: Int,
    @SerializedName("dependencia") val dependence: String? = null,
    @SerializedName("residencias_id") val residenceId: Int? = null,
)

data class DependenceResponse(@SerializedName("data") val data: List<Dependence>? = null)

data class Price(
    @SerializedName("id") val id: Int,
    @SerializedName("precio") val price: String? = null,
)

data class PriceResponse(@SerializedName("data") val data: List<Price>? = null)
