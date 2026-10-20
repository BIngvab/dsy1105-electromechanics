
package com.dominguezdiaz.electrotrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

import com.dominguezdiaz.electrotrack.ui.screen.InicioSesion
import com.dominguezdiaz.electrotrack.ui.screen.MenuUsuario
import com.dominguezdiaz.electrotrack.ui.screen.MenuAdministrador
import com.dominguezdiaz.electrotrack.ui.screen.RegistrarAsistencia
import com.dominguezdiaz.electrotrack.ui.screen.RevisarAsistencias

object Routes {
    const val LOGIN = "login"
    const val USUARIO = "usuario"
    const val ADMINISTRADOR = "administrador"
    const val ASISTENCIA = "asistencia"
    const val REVISAR_ASISTENCIAS = "revisar_asistencias"

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

                        navController.navigate(Routes.USUARIO) {

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


        composable(Routes.ASISTENCIA) {

            RegistrarAsistencia(
                onVolver = {
                    navController.popBackStack()

                }

            )

        }


        composable(Routes.USUARIO) {

            MenuUsuario(
                onCerrarSesion = {

                    navController.navigate(Routes.LOGIN) {

                        popUpTo(Routes.USUARIO) {
                            inclusive = true

                        }

                        launchSingleTop = true

                    }

                },
                onRegistrarAsistencia = {
                    navController.navigate(Routes.ASISTENCIA)

                }

            )

        }


        composable(Routes.ADMINISTRADOR) {

            MenuAdministrador(
                onCerrarSesion = {

                    navController.navigate(Routes.LOGIN) {

                        popUpTo(Routes.ADMINISTRADOR) {
                            inclusive = true

                        }

                        launchSingleTop = true

                    }

                },
                onRevisarAsistencias = {
                    navController.navigate(Routes.REVISAR_ASISTENCIAS)

                }

            )

        }


        composable(Routes.REVISAR_ASISTENCIAS) {

            RevisarAsistencias(
                onVolver = {
                    navController.popBackStack()

                }

            )

        }

    }

}
