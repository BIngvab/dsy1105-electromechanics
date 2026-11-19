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
import com.dominguezdiaz.electrotrack.data.MIGRATION_4_5
import com.dominguezdiaz.electrotrack.data.MIGRATION_5_6
import com.dominguezdiaz.electrotrack.data.remote.AsistenciaDto
import com.dominguezdiaz.electrotrack.data.remote.RetrofitClient
import kotlinx.coroutines.CancellationException
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
            MIGRATION_3_4,
            MIGRATION_4_5,
            MIGRATION_5_6

        )
        .build()


    private val asistenciaDao = baseDatos.asistenciaDao()

    private val asistenciaApi = RetrofitClient.asistenciaApi

    private val _asistencias = MutableStateFlow<List<Asistencia>>(emptyList())
    val asistencias = _asistencias.asStateFlow()


    init {
        obtenerAsistencias()
        sincronizarAsistencias()

    }


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

            // Guardamos primero la asistencia en Room
            asistenciaDao.registrarAsistencia(asistencia)

            // Actualizamos la lista local
            _asistencias.value = asistenciaDao.obtenerAsistencias()

            // Intentamos sincronizar los registros pendientes
            sincronizarAsistenciasPendientes()

        }
    }


    fun obtenerAsistencias() {
        viewModelScope.launch {
            _asistencias.value = asistenciaDao.obtenerAsistencias()

        }
    }


    fun sincronizarAsistencias() {
        viewModelScope.launch {
            sincronizarAsistenciasPendientes()

        }
    }


    private suspend fun sincronizarAsistenciasPendientes() {
        val pendientes = asistenciaDao.obtenerAsistenciasPendientes()

        for (asistencia in pendientes) {
            try {
                val asistenciaRemota = AsistenciaDto(
                    rutTrabajador = "99.999.999-9",
                    tipo = asistencia.tipo,
                    fechaHora = asistencia.fechaHora,
                    latitud = asistencia.latitud,
                    longitud = asistencia.longitud,
                    uuid = asistencia.uuid

                )


                asistenciaApi.registrarAsistencia(asistenciaRemota)

                asistenciaDao.marcarComoSincronizada(asistencia.uuid)

                Log.d(
                    "ElectroTrack",
                    "Asistencia sincronizada: ${asistencia.uuid}"

                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {
                Log.e(
                    "ElectroTrack",
                    "No se pudo sincronizar: ${asistencia.uuid}",
                    e

                )
            }
        }

        _asistencias.value = asistenciaDao.obtenerAsistencias()
    }
}
