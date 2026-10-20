package com.dominguezdiaz.electrotrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dominguezdiaz.electrotrack.data.Asistencia
import com.dominguezdiaz.electrotrack.data.ElectroTrackDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AsistenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val baseDatos = Room.databaseBuilder(
        application,
        ElectroTrackDatabase::class.java,
        "electrotrack_db"

    ).build()

    private val asistenciaDao = baseDatos.asistenciaDao()

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

            asistenciaDao.registrarAsistencia(asistencia)
            obtenerAsistencias()

        }
    }

    fun obtenerAsistencias() {
        viewModelScope.launch {
            _asistencias.value = asistenciaDao.obtenerAsistencias()

        }
    }
}
