package com.dominguezdiaz.electrotrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import androidx.room.ColumnInfo


@Entity(tableName = "asistencias")
data class Asistencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: String,
    val fechaHora: String,
    val latitud: Double,
    val longitud: Double,

    @ColumnInfo(defaultValue = "''")
    val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(defaultValue = "0")
    val sincronizada: Boolean = false

)