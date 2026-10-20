package com.dominguezdiaz.electrotrack.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MenuAdministrador(
    onCerrarSesion: () -> Unit,
    onRevisarAsistencias: () -> Unit,
    onAsignarTrabajo: () -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "ElectroTrack",
            style = MaterialTheme.typography.headlineLarge

        )


        Spacer(modifier = Modifier.height(8.dp))

        Text("Menu de Administrador")

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { onRevisarAsistencias() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Revisar asistencias")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { onAsignarTrabajo() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Asignar trabajos")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Revisar informes")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Gestionar estados de trabajos")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { onCerrarSesion() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Cerrar sesión")

        }

    }

}
