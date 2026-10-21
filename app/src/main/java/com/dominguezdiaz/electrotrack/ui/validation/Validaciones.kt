package com.dominguezdiaz.electrotrack.ui.validation

object Validaciones {
    fun validarRut(valor: String): String? = when {
        valor.isBlank() -> "El RUT es obligatorio."
        !Regex("""^\d{1,2}\.\d{3}\.\d{3}-[\dkK]$""").matches(valor.trim()) ->
            "Formato requerido: 99.999.999-9."
        else -> null
    }

    fun validarContrasena(valor: String): String? =
        if (valor.isBlank()) "La contraseña es obligatoria." else null

    fun validarTitulo(valor: String): String? = when {
        valor.isBlank() -> "El título es obligatorio."
        valor.trim().length < 3 -> "Escribe al menos 3 caracteres."
        else -> null
    }

    fun validarDescripcion(valor: String): String? = when {
        valor.isBlank() -> "La descripción es obligatoria."
        valor.trim().length < 5 -> "Escribe al menos 5 caracteres."
        else -> null
    }

    fun validarTrabajador(valor: String): String? =
        if (valor.isBlank()) "Debes indicar un trabajador." else null

    fun validarNumeroTrabajo(valor: String): String? =
        if (valor.toIntOrNull()?.let { it > 0 } != true)
            "Ingresa un número de trabajo mayor que cero." else null

    fun validarObservaciones(valor: String): String? =
        if (valor.isBlank()) "Las observaciones son obligatorias." else null
}
