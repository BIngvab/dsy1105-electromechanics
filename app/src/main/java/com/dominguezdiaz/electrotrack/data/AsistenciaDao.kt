package com.dominguezdiaz.electrotrack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AsistenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarAsistencia(asistencia: Asistencia)


    @Query("SELECT * FROM asistencias ORDER BY id DESC")
    suspend fun obtenerAsistencias(): List<Asistencia>


    @Query("SELECT * FROM asistencias WHERE sincronizada = 0")
    suspend fun obtenerAsistenciasPendientes(): List<Asistencia>


    @Query("SELECT * FROM asistencias WHERE uuid = :uuid LIMIT 1")
    suspend fun obtenerAsistenciaPorUuid(uuid: String): Asistencia?


    @Query("UPDATE asistencias SET sincronizada = 1 WHERE uuid = :uuid")
    suspend fun marcarComoSincronizada(uuid: String)

}
