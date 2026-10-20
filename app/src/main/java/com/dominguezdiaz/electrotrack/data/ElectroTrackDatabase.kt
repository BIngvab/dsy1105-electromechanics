package com.dominguezdiaz.electrotrack.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Asistencia::class],
    version = 1,
    exportSchema = false

)

abstract class ElectroTrackDatabase : RoomDatabase() {
    abstract fun asistenciaDao(): AsistenciaDao


}