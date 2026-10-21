package com.dominguezdiaz.electrotrack.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.ui.validation.CampoValidado
import com.dominguezdiaz.electrotrack.ui.validation.Validaciones
import com.dominguezdiaz.electrotrack.ui.viewmodel.LoginViewModel

@Composable
fun InicioSesion(
    onLoginSuccess: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    var rut by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var errorRut by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }
    var mostrarTitulo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { mostrarTitulo = true }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = mostrarTitulo,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -40 })
        ) {
            Text("ElectroTrack", style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(Modifier.height(8.dp))
        Text("Inicio de sesión")
        Spacer(Modifier.height(24.dp))
        CampoValidado(
            valor = rut,
            alCambiar = { rut = it; errorRut = null; mensaje = "" },
            etiqueta = "RUT", error = errorRut, singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        CampoValidado(
            valor = contrasena,
            alCambiar = { contrasena = it; errorContrasena = null; mensaje = "" },
            etiqueta = "Contraseña", error = errorContrasena,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                errorRut = Validaciones.validarRut(rut)
                errorContrasena = Validaciones.validarContrasena(contrasena)
                if (errorRut == null && errorContrasena == null) {
                    val resultado = loginViewModel.validarLogin(rut.trim(), contrasena)
                    if (resultado == "USUARIO" || resultado == "ADMINISTRADOR") {
                        mensaje = "Inicio de sesión exitoso."
                        onLoginSuccess(resultado)
                    } else {
                        mensaje = "RUT o contraseña incorrectos."
                    }
                } else {
                    mensaje = "Corrige los campos indicados."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Iniciar sesión") }
        Spacer(Modifier.height(12.dp))
        AnimatedVisibility(mensaje.isNotEmpty()) {
            Text(mensaje, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(20.dp))
        Text("Datos de prueba:")
        Text("RUT: 99.999.999-9")
        Text("Contraseña: 42368")
    }
}
