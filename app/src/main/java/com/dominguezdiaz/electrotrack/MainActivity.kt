package com.dominguezdiaz.electrotrack
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dominguezdiaz.electrotrack.ui.theme.ElectroTrackTheme
import com.dominguezdiaz.electrotrack.ui.navigation.AppNavigation

class MainActivity : ComponentActivity () {
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ElectroTrackTheme {
                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()

                    ) {
                        AppNavigation()

                    }
                }
            }
        }
    }
}



@Composable
fun PantallaPrincipal() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Text(
            text = "ElectroTrack",
            style = MaterialTheme.typography.headlineLarge

        )


        Text(
            text = "Sistema de gestión de mantenimiento industrial"
        )


        Text(
            text = "Bienvenido a ElectroTrack"
        )
    }
}