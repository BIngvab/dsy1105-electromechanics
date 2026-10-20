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
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.location.LocationManager
import android.content.Context
import android.os.Build


@Composable
fun RegistrarAsistencia(onVolver: () -> Unit) {
    val fechaHoraActual = LocalDateTime.now()
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val fechaHoraFormateada = fechaHoraActual.format(formato)
    val context = LocalContext.current


    var ubicacion by remember {
        mutableStateOf("Pendiente")

    }


    var permisoUbicacion by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION

            ) == PackageManager.PERMISSION_GRANTED
        )
    }


    val solicitarPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()

    ) { permisoConcedido ->
        permisoUbicacion = permisoConcedido

    }

    fun obtenerUbicacion() {
        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE

        ) as LocationManager


        val permisoPreciso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION

        ) == PackageManager.PERMISSION_GRANTED


        val permisoAproximado = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION

        ) == PackageManager.PERMISSION_GRANTED


        if (permisoPreciso || permisoAproximado) {
            try {
                val proveedor = if (
                    permisoPreciso &&
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                ) {
                    LocationManager.GPS_PROVIDER

                } else if (
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                ) {
                    LocationManager.NETWORK_PROVIDER

                } else {
                    ubicacion = "Activa la ubicación del dispositivo."
                    return

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                    locationManager.getCurrentLocation(
                        proveedor,
                        null,
                        ContextCompat.getMainExecutor(context)

                    ) { location ->
                        if (location != null) {
                            ubicacion = "Latitud: %.6f\nLongitud: %.6f".format(
                                java.util.Locale.US,
                                location.latitude,
                                location.longitude

                            ) } else {
                            ubicacion = "No se pudo obtener la ubicación"

                        }
                    }
                } else {
                    val location = locationManager.getLastKnownLocation(proveedor)

                    if (location != null) {
                        ubicacion = "Latitud: %.6f\nLongitud: %.6f".format(
                            java.util.Locale.US,
                            location.latitude,
                            location.longitude

                        )

                    } else {
                        ubicacion = "No hay una ubicación disponible."

                    }
                }
            }  catch (e: SecurityException) {
                ubicacion = "Permiso de ubicación no concedido."

            }   catch (e: Exception) {
                ubicacion = "Error al obtener la ubicación."

            }
        } else {
            ubicacion = "Permiso de ubicación no concedido."

        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Registrar Asistencia",
            style = MaterialTheme.typography.headlineLarge

        )


        Spacer(modifier = Modifier.height(12.dp))

        Text("Ubicación: $ubicacion")

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {obtenerUbicacion()},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Obtener ubicación.")

        }


        Button(
            onClick = {
                if (!permisoUbicacion) {
                    solicitarPermiso.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION

                    )
                }
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                if (permisoUbicacion) {
                    "Permiso de ubicación concedido."

                } else {
                    "Permitir ubicación."

                }
            )


            Spacer(modifier = Modifier.height(12.dp))

            Text("Fecha y hora: $fechaHoraFormateada")

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()

            ) {
                Text("Registrar entrada.")

            }


            Spacer(modifier = Modifier.height(12.dp))


            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()

            ) {
                Text("Registrar salida.")

            }


            Spacer(modifier = Modifier.height(12.dp))


            Button(
                onClick = { onVolver() },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text("Volver al menú.")

            }
        }
    }
}
