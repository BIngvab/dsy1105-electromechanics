package com.dominguezdiaz.electrotrack.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dominguezdiaz.electrotrack.ui.viewmodel.LoginViewModel

@Composable
fun InicioSesion(
    onLoginSuccess: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel()

) {

    var rut by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

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

        Text("Inicio de sesión")

        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("Rut") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

        )


        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true

        )


        Spacer(modifier = Modifier.height(20.dp))


        Button (
            onClick = {

                val resultado = loginViewModel.validarLogin(rut, contrasena)

                if (resultado == "USUARIO" || resultado == "ADMINISTRADOR") {
                    mensaje = "Inicio de sesión exitoso"
                    onLoginSuccess(resultado)

                } else {
                    mensaje = "Inicio de sesion fallido, por favor revise su rut y contraseña"

                }
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Iniciar Sesión")

        }


        Spacer(modifier = Modifier.height(12.dp))


        Text(
            text = mensaje,
            color = if (mensaje == "Inicio de sesión exitoso")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error

        )


        Spacer(modifier = Modifier.height(20.dp))


        Text("Datos de Prueba: ")
        Text("rut: 99.999.999-9")
        Text("contraseña: 42368")


    }
}