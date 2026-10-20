package com.dominguezdiaz.electrotrack.data
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Asistencia::class, Trabajo::class],
    version = 2,
    exportSchema = false

)
abstract class ElectroTrackDatabase : RoomDatabase() {

    abstract fun asistenciaDao(): AsistenciaDao

    abstract fun trabajoDao(): TrabajoDao

}
