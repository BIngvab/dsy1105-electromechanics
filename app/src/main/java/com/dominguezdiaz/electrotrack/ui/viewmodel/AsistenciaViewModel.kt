package com.dominguezdiaz.electrotrack.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dominguezdiaz.electrotrack.data.Asistencia
import com.dominguezdiaz.electrotrack.data.ElectroTrackDatabase
import com.dominguezdiaz.electrotrack.data.MIGRATION_1_2
import com.dominguezdiaz.electrotrack.data.MIGRATION_2_3
import com.dominguezdiaz.electrotrack.data.MIGRATION_3_4
import com.dominguezdiaz.electrotrack.data.remote.AsistenciaDto
import com.dominguezdiaz.electrotrack.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AsistenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val baseDatos = Room.databaseBuilder(
        application,
        ElectroTrackDatabase::class.java,
        "electrotrack_db"

    )
        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3,
            MIGRATION_3_4

        )
        .build()


    private val asistenciaDao = baseDatos.asistenciaDao()

    private val asistenciaApi = RetrofitClient.asistenciaApi

    private val _asistencias = MutableStateFlow<List<Asistencia>>(emptyList())
    val asistencias = _asistencias.asStateFlow()


    fun registrarAsistencia(
        tipo: String,
        fechaHora: String,
        latitud: Double,
        longitud: Double

    ) {
        viewModelScope.launch {
            val asistencia = Asistencia(
                tipo = tipo,
                fechaHora = fechaHora,
                latitud = latitud,
                longitud = longitud

            )


            // Guardamos la asistencia localmente con Room
            asistenciaDao.registrarAsistencia(asistencia)

            // Actualizamos la lista local
            obtenerAsistencias()


            // Enviamos la asistencia al microservicio
            try {
                val asistenciaRemota = AsistenciaDto(
                    rutTrabajador = "99.999.999-9",
                    tipo = tipo,
                    fechaHora = fechaHora,
                    latitud = latitud,
                    longitud = longitud

                )

                asistenciaApi.registrarAsistencia(asistenciaRemota)

                Log.d("ElectroTrack", "Asistencia enviada al microservicio")

            } catch (e: Exception) {

                Log.e(
                    "ElectroTrack",
                    "No se pudo enviar la asistencia al microservicio",
                    e

                )
            }
        }
    }

    fun obtenerAsistencias() {
        viewModelScope.launch {
            _asistencias.value = asistenciaDao.obtenerAsistencias()

        }
    }
}
