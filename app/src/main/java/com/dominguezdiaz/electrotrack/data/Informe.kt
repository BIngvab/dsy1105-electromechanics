package com.dominguezdiaz.electrotrack.data
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "informes")
data class Informe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trabajoId: Int,
    val descripcion: String,
    val observaciones: String,
    val fechaHora: String

)
