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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.dominguezdiaz.electrotrack.data.remote.ClimaDto
import com.dominguezdiaz.electrotrack.data.remote.ClimaRetrofitClient

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch


@Composable
fun ConsultarClima(
    onVolver: () -> Unit

) {
    val scope = rememberCoroutineScope()


    var latitud by remember { mutableStateOf("-33.4489") }
    var longitud by remember { mutableStateOf("-70.6693") }

    var clima by remember { mutableStateOf<ClimaDto?>(null) }
    var cargando by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }


    fun consultarClima() {

        val lat = latitud.replace(",", ".").toDoubleOrNull()
        val lon = longitud.replace(",", ".").toDoubleOrNull()


        if (lat == null || lon == null ||
            lat !in -90.0..90.0 ||
            lon !in -180.0..180.0) {

            mensaje = "Ingresa coordenadas válidas."
            clima = null

            return

        }


        scope.launch {

            cargando = true
            mensaje = ""
            clima = null


            try {

                clima = ClimaRetrofitClient.climaApi.obtenerClima(
                    latitud = lat,
                    longitud = lon

                )

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                mensaje = "No se pudo consultar el clima. Revisa tu conexión a Internet."

            } finally {

                cargando = false

            }

        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "Clima en terreno",
            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(modifier = Modifier.height(12.dp))


        Text("Consulta meteorológica mediante Open-Meteo")


        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value = latitud,
            onValueChange = { latitud = it },
            label = { Text("Latitud") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = longitud,
            onValueChange = { longitud = it },
            label = { Text("Longitud") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { consultarClima() },
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Consultar clima")

        }


        Spacer(modifier = Modifier.height(16.dp))


        if (cargando) {

            CircularProgressIndicator()

        }


        if (mensaje.isNotEmpty()) {

            Text(mensaje)

        }


        clima?.let { resultado ->

            Text(
                text = "Condiciones actuales",
                style = MaterialTheme.typography.titleLarge

            )


            Spacer(modifier = Modifier.height(12.dp))


            Text("Temperatura: ${resultado.current.temperatura} °C")

            Text("Humedad: ${resultado.current.humedad} %")

            Text("Precipitación: ${resultado.current.precipitacion} mm")

            Text("Viento: ${resultado.current.velocidadViento} km/h")

            Text("Actualización: ${resultado.current.time}")


            Spacer(modifier = Modifier.height(16.dp))


            if (resultado.current.precipitacion > 0.0) {

                Text(
                    text = "Precaución: se registran precipitaciones. Evalúa las condiciones antes de realizar trabajos eléctricos exteriores.",
                    color = MaterialTheme.colorScheme.error

                )

            } else {

                Text(
                    "No se registran precipitaciones en la medición actual. Verifica igualmente las condiciones de seguridad en terreno."

                )

            }

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
