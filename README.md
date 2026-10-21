# ElectroTrack
## Integrantes del equipo

- Fabian Dominguez Rojas
- Damian Diaz

**Institución:** Duoc UC  
**Asignatura:** DSY1105 — Desarrollo de Aplicaciones Móviles  
**Caso:** Electromechanics Ingeniería SpA

## Descripción del proyecto

ElectroTrack es una aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, orientada a apoyar la gestión de trabajos de mantenimiento electromecánico.

La aplicación permite que los trabajadores registren su asistencia, consulten sus trabajos asignados y elaboren informes de mantenimiento. Los administradores pueden asignar trabajos, gestionar sus estados y revisar las asistencias e informes registrados.

Este proyecto corresponde a un prototipo académico desarrollado para la asignatura DSY1105.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- Arquitectura MVVM
- Room Database
- Navigation Compose
- GPS y servicios de ubicación
- Corrutinas y StateFlow
- Git y GitHub
- Cámara de Android

## Funcionalidades

### Rol Usuario

- Inicio de sesión mediante credenciales de prueba.
- Registro de entrada y salida con fecha, hora y ubicación GPS.
- Consulta de trabajos asignados.
- Registro de informes de mantenimiento asociados a un número de trabajo.
- Navegación entre pantallas.

### Rol Administrador

- Inicio de sesión mediante credenciales de prueba.
- Revisión de registros de asistencia.
- Asignación de trabajos a trabajadores.
- Consulta y actualización del estado de los trabajos.
- Revisión de informes de mantenimiento.
- Navegación entre pantallas.

## Estados de los trabajos

Los trabajos pueden tener los siguientes estados:

- Pendiente
- En proceso
- Finalizado

## Persistencia de datos

La aplicación utiliza Room Database para almacenar localmente:

- Registros de asistencia.
- Trabajos asignados.
- Informes de mantenimiento.

Los datos se conservan al cerrar y volver a abrir la aplicación, siempre que no se eliminen sus datos locales.

## Credenciales de prueba

**Usuario**

- RUT: `99.999.999-9`
- Contraseña: `42368`

**Administrador**

- RUT: `88.888.888-8`
- Contraseña: `12345`

Estas credenciales son utilizadas únicamente para demostrar el funcionamiento del prototipo.

## Requisitos para ejecutar

- Android Studio.
- SDK de Android instalado.
- Emulador Android o dispositivo físico compatible.
- Conexión a internet para descargar dependencias cuando sea necesario.

## Instrucciones de ejecución

1. Clonar el repositorio desde GitHub.
2. Abrir la carpeta del proyecto en Android Studio.
3. Esperar la sincronización de Gradle.
4. Seleccionar un emulador o dispositivo compatible.
5. Ejecutar la aplicación con el botón Run.
6. Iniciar sesión utilizando las credenciales de prueba.

## Arquitectura

El proyecto utiliza el patrón MVVM (Model-View-ViewModel).

- **Model:** entidades y DAO de Room para el almacenamiento de información.
- **View:** pantallas construidas con Jetpack Compose.
- **ViewModel:** gestión de estados y comunicación entre las pantallas y los datos.
- **Navigation:** definición de rutas y navegación entre pantallas.

## Recursos nativos y animaciones

- GPS: registro de ubicación, fecha y hora de las asistencias.
- Cámara: captura de fotografías como evidencia en los informes de mantenimiento.
- Almacenamiento local: las fotografías se guardan en el almacenamiento privado de la aplicación y sus rutas se registran mediante Room.
- Animación: aparición del título ElectroTrack mediante Jetpack Compose.

Las fotografías son opcionales y la cámara utiliza una captura de vista previa.

## Alcance del prototipo

ElectroTrack es una aplicación académica de demostración. La autenticación utiliza credenciales predefinidas, la identificación del trabajador de prueba es fija y los datos se almacenan localmente en el dispositivo.

No incorpora un servidor remoto ni sincronización de datos entre diferentes dispositivos.

## Repositorio

https://github.com/BIngvab/dsy1105-electromechanics

## Asignatura

DSY1105 — Desarrollo de Aplicaciones Móviles.