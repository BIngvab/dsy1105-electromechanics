package com.dominguezdiaz.electrotrack.data.remote

import com.google.gson.annotations.SerializedName


data class ClimaDto(
    val latitude: Double,
    val longitude: Double,
    val current: ClimaActualDto

)


data class ClimaActualDto(
    val time: String,

    @SerializedName("temperature_2m")
    val temperatura: Double,

    @SerializedName("relative_humidity_2m")
    val humedad: Int,

    @SerializedName("precipitation")
    val precipitacion: Double,

    @SerializedName("wind_speed_10m")
    val velocidadViento: Double

)
