package com.dominguezdiaz.electrotrack.data
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trabajos")
data class Trabajo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val trabajador: String,
    val estado: String = "Pendiente"

)
