package com.dominguezdiaz.electrotrack.ui.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dominguezdiaz.electrotrack.data.ElectroTrackDatabase
import com.dominguezdiaz.electrotrack.data.Informe
import com.dominguezdiaz.electrotrack.data.MIGRATION_1_2
import com.dominguezdiaz.electrotrack.data.MIGRATION_2_3
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class InformeViewModel(application: Application) : AndroidViewModel(application) {

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


    private val informeDao = baseDatos.informeDao()


    private val _informes = MutableStateFlow<List<Informe>>(emptyList())


    val informes = _informes.asStateFlow()


    fun registrarInforme(
        trabajoId: Int,
        descripcion: String,
        observaciones: String,
        fechaHora: String

    ) {
        viewModelScope.launch {
            val informe = Informe(
                trabajoId = trabajoId,
                descripcion = descripcion,
                observaciones = observaciones,
                fechaHora = fechaHora

            )
            informeDao.registrarInforme(informe)


            obtenerInformes()

        }

    }


    fun obtenerInformes() {
        viewModelScope.launch {
            _informes.value = informeDao.obtenerInformes()

        }

    }


    fun obtenerInformesPorTrabajo(trabajoId: Int) {
        viewModelScope.launch {
            _informes.value = informeDao.obtenerInformesPorTrabajo(trabajoId)

        }

    }

}
