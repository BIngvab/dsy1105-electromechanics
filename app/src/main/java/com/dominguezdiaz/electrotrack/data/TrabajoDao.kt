package com.dominguezdiaz.electrotrack.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TrabajoDao {

    @Insert
    suspend fun registrarTrabajo(trabajo: Trabajo)


    @Query("SELECT * FROM trabajos ORDER BY id DESC")
    suspend fun obtenerTrabajos(): List<Trabajo>


    @Query("SELECT * FROM trabajos WHERE trabajador = :trabajador ORDER BY id DESC")
    suspend fun obtenerTrabajosPorTrabajador(trabajador: String): List<Trabajo>


    @Query("UPDATE trabajos SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, estado: String)

}
