package com.dominguezdiaz.electrotrack.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.ui.viewmodel.TrabajoViewModel


@Composable
fun AsignarTrabajo(
    onVolver: () -> Unit,
    trabajoViewModel: TrabajoViewModel = viewModel()

) {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var trabajador by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "Asignar trabajo",
            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título del trabajo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = trabajador,
            onValueChange = { trabajador = it },
            label = { Text("Trabajador asignado") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {
                if (
                    titulo.isBlank() ||
                    descripcion.isBlank() ||
                    trabajador.isBlank()

                ) {
                    mensaje = "Por favor complete todos los campos."

                } else {
                    trabajoViewModel.registrarTrabajo(
                        titulo = titulo.trim(),
                        descripcion = descripcion.trim(),
                        trabajador = trabajador.trim()

                    )


                    mensaje = "Registro del trabajo solicitado."


                    titulo = ""
                    descripcion = ""
                    trabajador = ""

                }

            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Guardar trabajo")

        }


        if (mensaje.isNotEmpty()) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(mensaje)

        }


        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { onVolver() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Volver al menú")

        }

    }

}
