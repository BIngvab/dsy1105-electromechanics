package com.dominguezdiaz.electrotrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "asistencias")
data class Asistencia (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val fechaHora: String,
    val latitud: Double,
    val longitud: Double
)
