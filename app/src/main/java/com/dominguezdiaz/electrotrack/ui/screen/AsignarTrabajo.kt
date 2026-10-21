package com.dominguezdiaz.electrotrack.ui.screen

import androidx.compose.animation.AnimatedVisibility
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
import com.dominguezdiaz.electrotrack.ui.validation.CampoValidado
import com.dominguezdiaz.electrotrack.ui.validation.Validaciones
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
    var errorTitulo by remember { mutableStateOf<String?>(null) }
    var errorDescripcion by remember { mutableStateOf<String?>(null) }
    var errorTrabajador by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Asignar trabajo", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        CampoValidado(
            valor = titulo,
            alCambiar = { titulo = it; errorTitulo = null; mensaje = "" },
            etiqueta = "Título del trabajo", error = errorTitulo, singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        CampoValidado(
            valor = descripcion,
            alCambiar = { descripcion = it; errorDescripcion = null; mensaje = "" },
            etiqueta = "Descripción", error = errorDescripcion, minLines = 3
        )
        Spacer(Modifier.height(12.dp))
        CampoValidado(
            valor = trabajador,
            alCambiar = { trabajador = it; errorTrabajador = null; mensaje = "" },
            etiqueta = "Trabajador asignado (RUT de prueba)",
            error = errorTrabajador, singleLine = true
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                errorTitulo = Validaciones.validarTitulo(titulo)
                errorDescripcion = Validaciones.validarDescripcion(descripcion)
                errorTrabajador = Validaciones.validarTrabajador(trabajador)
                if (errorTitulo != null || errorDescripcion != null || errorTrabajador != null) {
                    mensaje = "Corrige los campos indicados."
                } else {
                    trabajoViewModel.registrarTrabajo(
                        titulo = titulo.trim(),
                        descripcion = descripcion.trim(),
                        trabajador = trabajador.trim()
                    )
                    mensaje = "Registro del trabajo solicitado."
                    titulo = ""; descripcion = ""; trabajador = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar trabajo") }
        Spacer(Modifier.height(12.dp))
        AnimatedVisibility(mensaje.isNotEmpty()) {
            Text(
                mensaje,
                color = if (mensaje.startsWith("Registro"))
                    MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al menú")
        }
    }
}
