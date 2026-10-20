package com.dominguezdiaz.electrotrack.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dominguezdiaz.electrotrack.ui.screen.InicioSesion
import com.dominguezdiaz.electrotrack.ui.screen.MenuUsuario
import com.dominguezdiaz.electrotrack.ui.screen.MenuAdministrador
import androidx.navigation.compose.composable




object Routes {
    const val LOGIN = "login"
    const val USUARIO = "usuario"
    const val ADMINISTRADOR = "administrador"

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN

    ) {
        composable(Routes.LOGIN) {
            InicioSesion(
                onLoginSuccess = { rol ->
                    if (rol == "USUARIO") {
                        navController.navigate(Routes.USUARIO){
                            popUpTo(Routes.LOGIN) {
                                inclusive = true

                            }
                        }
                    } else if (rol == "ADMINISTRADOR") {
                        navController.navigate(Routes.ADMINISTRADOR) {
                            popUpTo(Routes.LOGIN) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }


        composable (Routes.USUARIO) {
            MenuUsuario()
        }

        composable(Routes.ADMINISTRADOR) {
            MenuAdministrador()
        }




    }
}