package com.dominguezdiaz.electrotrack.ui.viewmodel
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun validarLogin(rut: String, contrasena: String): String {
        if (rut == "99.999.999-9" && contrasena == "42368") {
            return "USUARIO"

        }

        if (rut == "88.888.888-8" && contrasena == "12345") {
            return "ADMINISTRADOR"

        }

        return "ERROR"

    }
}