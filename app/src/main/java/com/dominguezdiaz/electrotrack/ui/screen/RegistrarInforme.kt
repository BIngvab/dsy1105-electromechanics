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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.ui.viewmodel.InformeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import androidx.compose.ui.platform.LocalContext


@Composable
fun RegistrarInforme(
    onVolver: () -> Unit,
    informeViewModel: InformeViewModel = viewModel()

) {
    val context = LocalContext.current
    var trabajoId by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var fotografia by remember { mutableStateOf<Bitmap?>(null) }


    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { imagen ->
        if (imagen != null) {
            fotografia = imagen
            mensaje = "Fotografía capturada correctamente."

        } else {
            mensaje = "No se capturó ninguna fotografía."

        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "Informe de mantenimiento",
            style = MaterialTheme.typography.headlineMedium

        )


        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = trabajoId,
            onValueChange = { trabajoId = it },
            label = { Text("Número del trabajo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción del mantenimiento") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = observaciones,
            onValueChange = { observaciones = it },
            label = { Text("Observaciones") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3

        )


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                camaraLauncher.launch(null)

            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Tomar fotografía")

        }


        Spacer(modifier = Modifier.height(12.dp))


        fotografia?.let { imagen ->
            Image(
                bitmap = imagen.asImageBitmap(),
                contentDescription = "Fotografía del mantenimiento",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit

            )


            Spacer(modifier = Modifier.height(12.dp))

        }


        Button(
            onClick = {
                val id = trabajoId.toIntOrNull()

                if (id == null || id <= 0 ||
                    descripcion.isBlank() ||
                    observaciones.isBlank()

                ) {
                    mensaje = "Completa todos los campos correctamente."

                } else {
                    val fechaHora = SimpleDateFormat(
                        "dd/MM/yyyy HH:mm",
                        Locale.getDefault()

                    ).format(Date())


                    val rutaFotografia = fotografia?.let { imagen ->
                        guardarFotografia(context, imagen)

                    }


                    if (fotografia != null && rutaFotografia == null) {
                        mensaje = "No se pudo guardar la fotografía."

                    } else {
                        informeViewModel.registrarInforme(
                            trabajoId = id,
                            descripcion = descripcion.trim(),
                            observaciones = observaciones.trim(),
                            fechaHora = fechaHora,
                            rutaFotografia = rutaFotografia

                        )


                        mensaje = "Se solicitó el registro del informe."
                        trabajoId = ""
                        descripcion = ""
                        observaciones = ""
                        fotografia = null

                    }

                }

            },
            modifier = Modifier.fillMaxWidth()



        ) {
            Text("Guardar informe")

        }


        Spacer(modifier = Modifier.height(12.dp))


        if (mensaje.isNotEmpty()) {
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


private fun guardarFotografia(
    context: Context,
    fotografia: Bitmap

): String? {
    return try {
        val carpeta = File(context.filesDir, "fotografias")

        if (!carpeta.exists()) {
            carpeta.mkdirs()

        }


        val archivo = File(
            carpeta,
            "evidencia_${UUID.randomUUID()}.jpg"

        )


        FileOutputStream(archivo).use { salida ->
            fotografia.compress(
                Bitmap.CompressFormat.JPEG,
                90,
                salida

            )

        }


        archivo.absolutePath

    } catch (e: Exception) {
        null

    }

}
