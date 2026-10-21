package com.dominguezdiaz.electrotrack.ui.validation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CampoValidado(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    error: String?,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        modifier = modifier.fillMaxWidth(),
        isError = error != null,
        supportingText = {
            AnimatedVisibility(visible = error != null, enter = fadeIn(), exit = fadeOut()) {
                Text(error.orEmpty(), color = MaterialTheme.colorScheme.error)
            }
        },
        trailingIcon = {
            if (error != null) Text("⚠", color = MaterialTheme.colorScheme.error)
        },
        minLines = minLines,
        singleLine = singleLine,
        visualTransformation = visualTransformation
    )
}
