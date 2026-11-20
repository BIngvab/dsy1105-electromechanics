package com.dominguezdiaz.electrotrack.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

import com.dominguezdiaz.electrotrack.ui.viewmodel.AsistenciaViewModel

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun RegistrarAsistencia(
    onVolver: () -> Unit,
    asistenciaViewModel: AsistenciaViewModel = viewModel()

) {
    val context = LocalContext.current

    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")


    var ubicacion by remember { mutableStateOf("Pendiente") }
    var latitud by remember { mutableStateOf<Double?>(null) }
    var longitud by remember { mutableStateOf<Double?>(null) }
    var mensaje by remember { mutableStateOf("") }


    var fechaHoraMostrada by remember {
        mutableStateOf(LocalDateTime.now().format(formato))

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


        if (!permisoConcedido) {
            mensaje = "Debes permitir la ubicación precisa para registrar asistencia."

        }
    }


    fun guardarUbicacion(location: Location) {

        latitud = location.latitude
        longitud = location.longitude


        ubicacion = "Latitud: %.6f\nLongitud: %.6f".format(
            Locale.US,
            location.latitude,
            location.longitude

        )


        mensaje = "Ubicación obtenida correctamente."
    }


    fun buscarUltimaUbicacion(locationManager: LocationManager): Location? {

        val proveedores = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER

        )


        val ubicaciones = proveedores.mapNotNull { proveedor ->

            try {

                if (locationManager.isProviderEnabled(proveedor)) {
                    locationManager.getLastKnownLocation(proveedor)

                } else {
                    null

                }

            } catch (e: SecurityException) {
                null

            } catch (e: Exception) {
                null

            }
        }

        return ubicaciones.maxByOrNull { it.time }

    }


    fun obtenerUbicacion() {

        latitud = null
        longitud = null

        ubicacion = "Buscando ubicación..."
        mensaje = ""


        val permisoPreciso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION

        ) == PackageManager.PERMISSION_GRANTED


        val permisoAproximado = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION

        ) == PackageManager.PERMISSION_GRANTED


        if (!permisoPreciso && !permisoAproximado) {

            ubicacion = "Permiso de ubicación no concedido."

            solicitarPermiso.launch(
                Manifest.permission.ACCESS_FINE_LOCATION

            )

            return

        }


        val locationManager = context.getSystemService(
            Context.LOCATION_SERVICE

        ) as LocationManager


        try {

            val proveedor = when {

                permisoPreciso &&
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    LocationManager.GPS_PROVIDER

                }


                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                    LocationManager.NETWORK_PROVIDER

                }


                else -> {
                    ubicacion = "Activa la ubicación del dispositivo."
                    return

                }
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                locationManager.getCurrentLocation(
                    proveedor,
                    null,
                    ContextCompat.getMainExecutor(context)

                ) { location ->

                    if (location != null) {

                        guardarUbicacion(location)

                    } else {

                        val ultimaUbicacion = buscarUltimaUbicacion(locationManager)


                        if (ultimaUbicacion != null) {

                            val antiguedad = System.currentTimeMillis() -
                                    ultimaUbicacion.time


                            if (antiguedad <= 5 * 60 * 1000) {

                                guardarUbicacion(ultimaUbicacion)

                                mensaje = "Se utilizó una ubicación reciente guardada por Android."

                            } else {

                                ubicacion = "No se pudo obtener una ubicación reciente."

                                mensaje = "Abre Google Maps, espera el punto azul y vuelve a intentar."

                            }

                        } else {

                            ubicacion = "No se pudo obtener la ubicación."

                            mensaje = "Abre Google Maps, espera el punto azul y vuelve a intentar."

                        }
                    }
                }

            } else {

                val ultimaUbicacion = buscarUltimaUbicacion(locationManager)


                if (ultimaUbicacion != null &&
                    System.currentTimeMillis() - ultimaUbicacion.time <= 5 * 60 * 1000) {

                    guardarUbicacion(ultimaUbicacion)

                } else {

                    ubicacion = "No hay una ubicación reciente disponible."

                    mensaje = "Abre Google Maps y vuelve a intentar."

                }
            }

        } catch (e: SecurityException) {

            ubicacion = "Permiso de ubicación no concedido."

        } catch (e: Exception) {

            ubicacion = "Error al obtener la ubicación."

            mensaje = e.localizedMessage ?: "Intenta nuevamente."

        }

    }


    fun guardarAsistencia(tipo: String) {

        val lat = latitud
        val lon = longitud


        if (lat == null || lon == null) {

            mensaje = "Primero debes obtener tu ubicación."

            return

        }


        val fechaHora = LocalDateTime.now().format(formato)


        asistenciaViewModel.registrarAsistencia(
            tipo = tipo,
            fechaHora = fechaHora,
            latitud = lat,
            longitud = lon

        )


        fechaHoraMostrada = fechaHora

        mensaje = "Registro de $tipo solicitado."

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
            onClick = { obtenerUbicacion() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Obtener ubicación")

        }


        Spacer(modifier = Modifier.height(12.dp))


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
                    "Permiso de ubicación concedido"

                } else {
                    "Permitir ubicación"

                }
            )
        }


        Spacer(modifier = Modifier.height(12.dp))


        Text("Fecha y hora: $fechaHoraMostrada")


        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { guardarAsistencia("ENTRADA") },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Registrar entrada")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { guardarAsistencia("SALIDA") },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Registrar salida")

        }


        if (mensaje.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(mensaje)

        }


        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = { onVolver() },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Volver al menú")

        }
    }
}
