package com.dominguezdiaz.electrotrack.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.dominguezdiaz.electrotrack.data.ElectroTrackDatabase
import com.dominguezdiaz.electrotrack.data.MIGRATION_1_2
import com.dominguezdiaz.electrotrack.data.MIGRATION_2_3
import com.dominguezdiaz.electrotrack.data.MIGRATION_3_4
import com.dominguezdiaz.electrotrack.data.MIGRATION_4_5
import com.dominguezdiaz.electrotrack.data.MIGRATION_5_6
import com.dominguezdiaz.electrotrack.data.Trabajo
import com.dominguezdiaz.electrotrack.data.remote.RetrofitClient
import com.dominguezdiaz.electrotrack.network.TrabajoDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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


    private val _trabajos = MutableStateFlow<List<Trabajo>>(emptyList())
    val trabajos = _trabajos.asStateFlow()


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

                Log.d(
                    "ElectroTrack",
                    "Servidor devolvio ${trabajosRemotos.size} trabajos"

                )


                for (trabajoDto in trabajosRemotos) {
                    guardarTrabajoRemoto(trabajoDto)

                }


                val idsRemotos = trabajosRemotos.mapNotNull { it.id }.toSet()

                val trabajosLocales = trabajoDao.obtenerTrabajos()


                for (trabajoLocal in trabajosLocales) {

                    val idServidor = trabajoLocal.idServidor

                    if (idServidor != null && idServidor !in idsRemotos) {
                        trabajoDao.eliminarTrabajoPorIdServidor(idServidor)

                    }

                }


                _trabajos.value = trabajoDao.obtenerTrabajos()

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


    fun editarTrabajo(
        id: Int,
        titulo: String,
        descripcion: String,
        trabajador: String

    ) {
        viewModelScope.launch {

            try {

                if (titulo.isBlank() || trabajador.isBlank()) {
                    Log.e(
                        "ElectroTrack",
                        "Titulo y trabajador son obligatorios"

                    )
                    return@launch

                }


                val trabajo = trabajoDao.obtenerTrabajoPorId(id)

                if (trabajo == null) {
                    Log.e("ElectroTrack", "Trabajo no encontrado: $id")
                    return@launch

                }


                val trabajoEditado = trabajo.copy(
                    titulo = titulo.trim(),
                    descripcion = descripcion.trim(),
                    trabajador = trabajador.trim()

                )


                val idServidor = trabajo.idServidor

                if (idServidor != null) {

                    val trabajoDto = TrabajoDto(
                        id = idServidor,
                        titulo = trabajoEditado.titulo,
                        descripcion = trabajoEditado.descripcion,
                        trabajador = trabajoEditado.trabajador,
                        estado = trabajoEditado.estado

                    )

                    val respuesta = trabajoApi.actualizarTrabajo(
                        idServidor,
                        trabajoDto

                    )

                    trabajoDao.actualizarTrabajo(
                        convertirATrabajo(respuesta).copy(
                            id = trabajo.id

                        )

                    )

                    Log.d(
                        "ElectroTrack",
                        "Trabajo editado en servidor: $idServidor"

                    )

                } else {

                    trabajoDao.actualizarTrabajo(trabajoEditado)

                    Log.d(
                        "ElectroTrack",
                        "Trabajo editado solo localmente: $id"

                    )

                }


                _trabajos.value = trabajoDao.obtenerTrabajos()

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                Log.e(
                    "ElectroTrack",
                    "Error al editar trabajo",
                    e

                )

            }

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


                _trabajos.value = trabajoDao.obtenerTrabajos()

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


    fun eliminarTrabajo(id: Int) {

        viewModelScope.launch {

            try {

                val trabajo = trabajoDao.obtenerTrabajoPorId(id)

                if (trabajo == null) {
                    Log.e("ElectroTrack", "Trabajo no encontrado: $id")
                    return@launch

                }


                val idServidor = trabajo.idServidor

                if (idServidor != null) {
                    trabajoApi.eliminarTrabajo(idServidor)

                }


                trabajoDao.eliminarTrabajo(id)

                _trabajos.value = trabajoDao.obtenerTrabajos()


                Log.d(
                    "ElectroTrack",
                    "Trabajo eliminado correctamente: $id"

                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                Log.e(
                    "ElectroTrack",
                    "Error al eliminar trabajo",
                    e

                )

            }

        }

    }

}
