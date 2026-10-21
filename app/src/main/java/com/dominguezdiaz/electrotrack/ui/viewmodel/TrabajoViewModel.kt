package com.dominguezdiaz.electrotrack.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dominguezdiaz.electrotrack.data.ElectroTrackDatabase
import com.dominguezdiaz.electrotrack.data.MIGRATION_1_2
import com.dominguezdiaz.electrotrack.data.Trabajo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.dominguezdiaz.electrotrack.data.MIGRATION_2_3


class TrabajoViewModel(application: Application) : AndroidViewModel(application) {

    private val baseDatos = Room.databaseBuilder(
        application,
        ElectroTrackDatabase::class.java,
        "electrotrack_db"

    )

        .addMigrations(
            MIGRATION_1_2,
            MIGRATION_2_3

        )
        .build()



    private val trabajoDao = baseDatos.trabajoDao()


    private val _trabajos = MutableStateFlow<List<Trabajo>>(emptyList())
    val trabajos = _trabajos.asStateFlow()


    fun registrarTrabajo(
        titulo: String,
        descripcion: String,
        trabajador: String

    ) {
        viewModelScope.launch {
            val trabajo = Trabajo(
                titulo = titulo,
                descripcion = descripcion,
                trabajador = trabajador

            )
            trabajoDao.registrarTrabajo(trabajo)
            obtenerTrabajos()

        }

    }


    fun obtenerTrabajos() {
        viewModelScope.launch {
            _trabajos.value = trabajoDao.obtenerTrabajos()

        }

    }


    fun obtenerTrabajosPorTrabajador(trabajador: String) {
        viewModelScope.launch {
            _trabajos.value = trabajoDao.obtenerTrabajosPorTrabajador(trabajador)

        }

    }


    fun actualizarEstado(id: Int, estado: String) {
        viewModelScope.launch {
            trabajoDao.actualizarEstado(id, estado)
            obtenerTrabajos()

        }

    }

}
