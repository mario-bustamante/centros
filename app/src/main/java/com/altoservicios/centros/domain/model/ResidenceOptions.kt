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
    @SerializedName("habitacion_dependencias_id") val roomResidenceId: Int? = null,
    @SerializedName("trato_id") val formOfAddress: Int? = 0,
    @SerializedName("descripcion") val description: String? = null,
    @SerializedName("precio") val price: Int? = 0,
    @SerializedName("disponible") val numAvailable: Int? = 0,
    @SerializedName("bo_bagno") val bathroom: Int? = 0,
    @SerializedName("updated_at") val updated_at: String? = null,
)

data class RoomResponse(@SerializedName("data") val data: List<Room>? = null)

data class RoomAvailability(
    @SerializedName("id") val id: Int,
    @SerializedName("disponible") val available: Int,
)

data class UpdateRoomsRequest(
    @SerializedName("residence_id") val residenceId: String,
    @SerializedName("habitaciones") val rooms: List<RoomAvailability>,
)

data class UpdateRoomsResponse(
    @SerializedName("data") val data: UpdateRoomsResult? = null,
)

data class UpdateRoomsResult(@SerializedName("updated") val updated: Int? = null)

data class Price(
    @SerializedName("min") val min: Int? = 1000,
    @SerializedName("max") val max: Int? = 10000,
    @SerializedName("initial") val initial: Int? = 1000,
    @SerializedName("step") val step: Int? = 10,
)

data class PriceResponse(@SerializedName("data") val data: List<Price>? = null)