package com.dominguezdiaz.electrotrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dominguezdiaz.electrotrack.ui.screen.InicioSesion
import androidx.navigation.compose.composable



object Routes {
    const val LOGIN = "login"

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN

    ) {
        composable(Routes.LOGIN) {
            InicioSesion()

        }
    }
}