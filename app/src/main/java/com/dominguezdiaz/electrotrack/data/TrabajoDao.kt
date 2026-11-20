package com.dominguezdiaz.electrotrack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TrabajoDao {

    @Insert
    suspend fun registrarTrabajo(trabajo: Trabajo): Long

    @Query("SELECT * FROM trabajos ORDER BY id DESC")
    suspend fun obtenerTrabajos(): List<Trabajo>

    @Query("SELECT * FROM trabajos WHERE trabajador = :trabajador ORDER BY id DESC")
    suspend fun obtenerTrabajosPorTrabajador(trabajador: String): List<Trabajo>

    @Query("UPDATE trabajos SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, estado: String)

    @Query("SELECT * FROM trabajos WHERE idServidor = :idServidor LIMIT 1")
    suspend fun obtenerTrabajoPorIdServidor(idServidor: Long): Trabajo?

    @Query("SELECT * FROM trabajos WHERE id = :id LIMIT 1")
    suspend fun obtenerTrabajoPorId(id: Int): Trabajo?

    @Update
    suspend fun actualizarTrabajo(trabajo: Trabajo)


    @Query("DELETE FROM trabajos WHERE id = :id")
    suspend fun eliminarTrabajo(id: Int)
}
