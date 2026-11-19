package com.dominguezdiaz.electrotrack.network

data class TrabajoDto(
    val id: Long? = null,
    val titulo: String,
    val descripcion: String,
    val trabajador: String,
    val estado: String = "Pendiente"

)
