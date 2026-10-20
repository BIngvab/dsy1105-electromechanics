package com.dominguezdiaz.electrotrack.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.ui.viewmodel.AsistenciaViewModel


@Composable
fun RevisarAsistencias(
    onVolver: () -> Unit,
    asistenciaViewModel: AsistenciaViewModel = viewModel()

) {
    val asistencias by asistenciaViewModel.asistencias.collectAsState()

    LaunchedEffect(Unit) {
        asistenciaViewModel.obtenerAsistencias()

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)

    ) {
        Text(
            text = "Revisar asistencias",
            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(modifier = Modifier.height(16.dp))


        if (asistencias.isEmpty()) {
            Text("No hay asistencias registradas.")

        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {
                items(asistencias) { asistencia ->
                    Card(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)

                        ) {
                            Text("Registro N° ${asistencia.id}")

                            Text("Tipo: ${asistencia.tipo}")

                            Text("Fecha y hora: ${asistencia.fechaHora}")

                            Text("Latitud: ${asistencia.latitud}")

                            Text("Longitud: ${asistencia.longitud}")

                        }

                    }

                }

            }

        }


        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { onVolver() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Volver al menú")

        }

    }

}
