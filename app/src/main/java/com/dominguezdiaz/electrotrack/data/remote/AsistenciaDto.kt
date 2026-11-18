package com.dominguezdiaz.electrotrack.data.remote

data class AsistenciaDto(
    val id: Long? = null,
    val rutTrabajador: String,
    val tipo: String,
    val fechaHora: String,
    val latitud: Double,
    val longitud: Double

)
