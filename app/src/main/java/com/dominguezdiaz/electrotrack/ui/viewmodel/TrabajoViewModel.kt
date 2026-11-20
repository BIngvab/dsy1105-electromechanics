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
import com.dominguezdiaz.electrotrack.data.MIGRATION_3_4
import com.dominguezdiaz.electrotrack.data.MIGRATION_4_5
import com.dominguezdiaz.electrotrack.data.MIGRATION_5_6
import com.dominguezdiaz.electrotrack.data.remote.RetrofitClient
import com.dominguezdiaz.electrotrack.network.TrabajoDto
import android.util.Log
import kotlinx.coroutines.CancellationException


class TrabajoViewModel(application: Application) : AndroidViewModel(application) {

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


    private val trabajoDao = baseDatos.trabajoDao()


    private val trabajoApi = RetrofitClient.trabajoApi


    private fun convertirATrabajo(trabajoDto: TrabajoDto): Trabajo {

        return Trabajo(
            titulo = trabajoDto.titulo,
            descripcion = trabajoDto.descripcion,
            trabajador = trabajoDto.trabajador,
            estado = trabajoDto.estado,
            idServidor = trabajoDto.id

        )

    }


    private suspend fun guardarTrabajoRemoto(trabajoDto: TrabajoDto) {

        val idServidor = trabajoDto.id ?: return

        val trabajoExistente = trabajoDao.obtenerTrabajoPorIdServidor(idServidor)

        if (trabajoExistente == null) {

            val trabajoNuevo = convertirATrabajo(trabajoDto)

            trabajoDao.registrarTrabajo(trabajoNuevo)

        } else {

            val trabajoActualizado = convertirATrabajo(trabajoDto).copy(
                id = trabajoExistente.id

            )

            trabajoDao.actualizarTrabajo(trabajoActualizado)

        }

    }


    fun sincronizarTrabajos() {

        viewModelScope.launch {

            try {

                val trabajosRemotos = trabajoApi.listarTrabajos()
                Log.d("ElectroTrack", "Servidor devolvió ${trabajosRemotos.size} trabajos")
                for (trabajo in trabajosRemotos) {
                    Log.d(
                        "ElectroTrack",
                        "Trabajo remoto: ID=${trabajo.id}, trabajador=${trabajo.trabajador}, titulo=${trabajo.titulo}"
                    )
                }

                for (trabajoDto in trabajosRemotos) {

                    guardarTrabajoRemoto(trabajoDto)

                }

                _trabajos.value = trabajoDao.obtenerTrabajosPorTrabajador(
                    "99.999.999-9"

                )

                Log.d(
                    "ElectroTrack",
                    "Trabajos sincronizados correctamente"

                )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                Log.e(
                    "ElectroTrack",
                    "Error al sincronizar trabajos",
                    e

                )

            }

        }

    }


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

            val idLocal = trabajoDao.registrarTrabajo(trabajo)

            obtenerTrabajos()

            try {

                val trabajoDto = TrabajoDto(
                    titulo = titulo,
                    descripcion = descripcion,
                    trabajador = trabajador,
                    estado = trabajo.estado

                )

                val trabajoRemoto = trabajoApi.registrarTrabajo(trabajoDto)

                val trabajoActualizado = convertirATrabajo(trabajoRemoto).copy(
                    id = idLocal.toInt()

                )

                trabajoDao.actualizarTrabajo(trabajoActualizado)

                obtenerTrabajos()

                Log.d(
                    "ElectroTrack",
                    "Trabajo registrado en el servidor correctamente"

                )

            } catch (e: CancellationException) {

                throw e

            } catch (e: Exception) {

                Log.e(
                    "ElectroTrack",
                    "Trabajo guardado localmente, pero no enviado al servidor",
                    e

                )

            }

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
            try {
                val trabajo = trabajoDao.obtenerTrabajoPorId(id)

                if (trabajo == null) {
                    Log.e("ElectroTrack", "Trabajo no encontrado: $id")
                    return@launch

                }

                val idServidor = trabajo.idServidor

                if (idServidor != null) {
                    val trabajoActualizado = trabajoApi.actualizarEstado(
                        idServidor,
                        estado

                    )

                    trabajoDao.actualizarEstado(
                        id,
                        trabajoActualizado.estado
                    )

                    Log.d(
                        "ElectroTrack",
                        "Estado actualizado en servidor: ${trabajoActualizado.estado}"
                    )
                } else {
                    trabajoDao.actualizarEstado(id, estado)

                    Log.d(
                        "ElectroTrack",
                        "Estado actualizado solo localmente"

                    )
                }

                _trabajos.value =
                    trabajoDao.obtenerTrabajosPorTrabajador("99.999.999-9")

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(
                    "ElectroTrack",
                    "Error al actualizar estado",
                    e

                )
            }
        }
    }


}
