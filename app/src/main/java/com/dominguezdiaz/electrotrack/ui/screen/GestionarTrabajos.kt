package com.dominguezdiaz.electrotrack.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.data.Trabajo
import com.dominguezdiaz.electrotrack.ui.viewmodel.TrabajoViewModel


@Composable
fun GestionarTrabajos(
    onVolver: () -> Unit,
    trabajoViewModel: TrabajoViewModel = viewModel()

) {
    val trabajos by trabajoViewModel.trabajos.collectAsState()

    var trabajoParaEditar by remember {
        mutableStateOf<Trabajo?>(null)

    }

    var trabajoParaEliminar by remember {
        mutableStateOf<Trabajo?>(null)

    }

    var tituloEditado by remember {
        mutableStateOf("")

    }

    var descripcionEditada by remember {
        mutableStateOf("")

    }

    var trabajadorEditado by remember {
        mutableStateOf("")

    }


    LaunchedEffect(Unit) {
        trabajoViewModel.sincronizarTrabajos()

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)

    ) {
        Text(
            text = "Gestionar trabajos",
            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(modifier = Modifier.height(16.dp))


        if (trabajos.isEmpty()) {
            Text("No hay trabajos registrados.")

        } else {

            trabajos.forEach { trabajo ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)

                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)

                    ) {
                        Text("Trabajo N° ${trabajo.id}")

                        Text("Título: ${trabajo.titulo}")

                        Text("Descripción: ${trabajo.descripcion}")

                        Text("Trabajador: ${trabajo.trabajador}")

                        Text("Estado actual: ${trabajo.estado}")


                        Spacer(modifier = Modifier.height(12.dp))


                        Button(
                            onClick = {
                                tituloEditado = trabajo.titulo
                                descripcionEditada = trabajo.descripcion
                                trabajadorEditado = trabajo.trabajador
                                trabajoParaEditar = trabajo

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text("Editar trabajo")

                        }


                        Spacer(modifier = Modifier.height(8.dp))


                        Button(
                            onClick = {
                                trabajoViewModel.actualizarEstado(
                                    trabajo.id,
                                    "Pendiente"

                                )

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text("Pendiente")

                        }


                        Button(
                            onClick = {
                                trabajoViewModel.actualizarEstado(
                                    trabajo.id,
                                    "En proceso"

                                )

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text("En proceso")

                        }


                        Button(
                            onClick = {
                                trabajoViewModel.actualizarEstado(
                                    trabajo.id,
                                    "Finalizado"

                                )

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text("Finalizado")

                        }


                        Spacer(modifier = Modifier.height(8.dp))


                        Button(
                            onClick = {
                                trabajoParaEliminar = trabajo

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text("Eliminar trabajo")

                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                onVolver()

            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Volver al menú")

        }

    }


    if (trabajoParaEditar != null) {

        AlertDialog(
            onDismissRequest = {
                trabajoParaEditar = null

            },
            title = {
                Text("Editar trabajo")

            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    OutlinedTextField(
                        value = tituloEditado,
                        onValueChange = {
                            tituloEditado = it

                        },
                        label = {
                            Text("Título")

                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()

                    )


                    OutlinedTextField(
                        value = descripcionEditada,
                        onValueChange = {
                            descripcionEditada = it

                        },
                        label = {
                            Text("Descripción")

                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 4

                    )


                    OutlinedTextField(
                        value = trabajadorEditado,
                        onValueChange = {
                            trabajadorEditado = it

                        },
                        label = {
                            Text("RUT del trabajador")

                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()

                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        val trabajo = trabajoParaEditar

                        if (trabajo != null) {

                            trabajoViewModel.editarTrabajo(
                                id = trabajo.id,
                                titulo = tituloEditado,
                                descripcion = descripcionEditada,
                                trabajador = trabajadorEditado

                            )
                        }

                        trabajoParaEditar = null

                    },
                    enabled = tituloEditado.isNotBlank() &&
                            trabajadorEditado.isNotBlank()

                ) {
                    Text("Guardar")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        trabajoParaEditar = null

                    }

                ) {
                    Text("Cancelar")

                }

            }

        )

    }


    if (trabajoParaEliminar != null) {

        AlertDialog(
            onDismissRequest = {
                trabajoParaEliminar = null

            },
            title = {
                Text("Eliminar trabajo")

            },
            text = {
                Text(
                    "¿Seguro que deseas eliminar este trabajo? Esta acción no se puede deshacer."

                )

            },
            confirmButton = {
                TextButton(
                    onClick = {

                        val trabajo = trabajoParaEliminar

                        if (trabajo != null) {
                            trabajoViewModel.eliminarTrabajo(trabajo.id)

                        }

                        trabajoParaEliminar = null

                    }

                ) {
                    Text("Eliminar")

                }

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        trabajoParaEliminar = null

                    }

                ) {
                    Text("Cancelar")

                }
            }
        )
    }
}
