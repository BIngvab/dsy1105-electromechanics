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
fun MenuUsuario(
    onCerrarSesion: () -> Unit,
    onRegistrarAsistencia: () -> Unit,
    onMisTrabajos: () -> Unit,
    onRegistrarInforme: () -> Unit

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

        Text("Menu Principal")

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {onRegistrarAsistencia()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Registrar asistencia")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {onMisTrabajos()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Mis trabajos asignados")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {onRegistrarInforme()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Informes de mantenimiento")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {onCerrarSesion()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Cerrar sesión")

        }


    }
}