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
import com.dominguezdiaz.electrotrack.ui.screen.AsignarTrabajo
import com.dominguezdiaz.electrotrack.ui.screen.MisTrabajos
import com.dominguezdiaz.electrotrack.ui.screen.GestionarTrabajos


object Routes {
    const val LOGIN = "login"
    const val USUARIO = "usuario"
    const val ADMINISTRADOR = "administrador"
    const val ASISTENCIA = "asistencia"
    const val REVISAR_ASISTENCIAS = "revisar_asistencias"
    const val ASIGNAR_TRABAJO = "asignar_trabajo"
    const val MIS_TRABAJOS = "mis_trabajos"
    const val GESTIONAR_TRABAJOS = "gestionar_trabajos"

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

                },
                onMisTrabajos = {
                    navController.navigate(Routes.MIS_TRABAJOS)

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

                },
                onAsignarTrabajo = {
                    navController.navigate(Routes.ASIGNAR_TRABAJO)

                },
                onGestionarTrabajos = {
                    navController.navigate(Routes.GESTIONAR_TRABAJOS)

                }

            )
        }



        composable(Routes.ASIGNAR_TRABAJO) {
            AsignarTrabajo(
                onVolver = {
                    navController.popBackStack()

                }
            )
        }


        composable(Routes.MIS_TRABAJOS) {
            MisTrabajos(
                onVolver = {
                    navController.popBackStack()

                }

            )

        }


        composable(Routes.GESTIONAR_TRABAJOS) {
            GestionarTrabajos(
                onVolver = {
                    navController.popBackStack()

                }

            )

        }


    }
}
