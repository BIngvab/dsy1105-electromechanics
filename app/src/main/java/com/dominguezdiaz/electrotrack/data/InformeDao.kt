package com.dominguezdiaz.electrotrack.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface InformeDao {

    @Insert
    suspend fun registrarInforme(informe: Informe)


    @Query("SELECT * FROM informes ORDER BY id DESC")
    suspend fun obtenerInformes(): List<Informe>


    @Query("SELECT * FROM informes WHERE trabajoId = :trabajoId ORDER BY id DESC")
    suspend fun obtenerInformesPorTrabajo(trabajoId: Int): List<Informe>

}
