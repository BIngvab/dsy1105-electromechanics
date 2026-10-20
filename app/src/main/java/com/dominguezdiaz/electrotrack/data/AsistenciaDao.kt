package com.dominguezdiaz.electrotrack.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AsistenciaDao {

    @Insert
    suspend fun registrarAsistencia(asistencia: Asistencia)

    @Query("SELECT * FROM asistencias ORDER BY id DESC")
    suspend fun obtenerAsistencias(): List<Asistencia>


}