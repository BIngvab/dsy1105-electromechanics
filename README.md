# ElectroTrack

**Aplicación móvil para la gestión de mantenimiento electromecánico**

## Integrantes

- Fabián Domínguez Rojas
- Damián Díaz

**Institución:** Duoc UC  
**Asignatura:** DSY1105 — Desarrollo de Aplicaciones Móviles  
**Caso de estudio:** Electromechanics Ingeniería SpA

## Descripción

ElectroTrack es una aplicación Android desarrollada en Kotlin y Jetpack Compose para apoyar la gestión de trabajos de mantenimiento electromecánico.

Permite registrar asistencias con ubicación GPS, administrar trabajos, consultar asignaciones y elaborar informes de mantenimiento. Incorpora almacenamiento local mediante Room y comunicación con microservicios REST desarrollados en Spring Boot.

Es un prototipo académico.

## Tecnologías

- Kotlin y Jetpack Compose
- Material Design 3
- Arquitectura MVVM
- Room Database
- Retrofit y Gson
- Corrutinas y StateFlow
- Navigation Compose
- GPS y cámara de Android
- Java 21 y Spring Boot
- PostgreSQL
- Maven
- JUnit 5, Mockito y JaCoCo
- Git y GitHub

## Funcionalidades

### Trabajador

- Inicio de sesión con credenciales de demostración.
- Registro de entrada y salida con fecha, hora y coordenadas GPS.
- Consulta de trabajos asignados.
- Registro de informes de mantenimiento.
- Captura opcional de fotografías como evidencia.
- Consulta de información meteorológica mediante una API externa.

### Administrador

- Inicio de sesión con credenciales de demostración.
- Revisión de asistencias registradas.
- Asignación y gestión de trabajos.
- Actualización de estados.
- Consulta de informes de mantenimiento.

## Arquitectura

La aplicación utiliza el patrón MVVM:

- **Model:** entidades, DAO y base de datos Room.
- **View:** interfaces desarrolladas con Jetpack Compose.
- **ViewModel:** gestión del estado y operaciones de datos.
- **Retrofit:** comunicación HTTP con servicios REST.

El backend está dividido en dos microservicios independientes:

| Microservicio | Puerto | Función |
|---|---|---|
| asistencias-service | 8081 | Gestión de registros de asistencia |
| trabajos-service | 8082 | Gestión de trabajos electromecánicos |

Cada microservicio utiliza PostgreSQL para persistir su información.

## Persistencia y sincronización

ElectroTrack combina almacenamiento local con comunicación hacia los microservicios.

Room permite conservar datos en el dispositivo. Retrofit permite enviar y consultar información del backend, incluyendo la sincronización de asistencias y trabajos.

La disponibilidad de la sincronización requiere que los microservicios estén ejecutándose y sean accesibles desde el dispositivo Android.

## API externa

La aplicación incorpora una consulta meteorológica mediante Retrofit, como integración con un servicio externo independiente de los microservicios propios.

## Operaciones REST

### Asistencias

- `GET /api/asistencias`
- `GET /api/asistencias/{id}`
- `POST /api/asistencias`
- `PUT /api/asistencias/{id}`
- `DELETE /api/asistencias/{id}`

### Trabajos

- `GET /api/trabajos`
- `GET /api/trabajos/trabajador/{trabajador}`
- `POST /api/trabajos`
- `PUT /api/trabajos/{id}`
- `PATCH /api/trabajos/{id}/estado`
- `DELETE /api/trabajos/{id}`

## Pruebas automatizadas

Se implementaron pruebas unitarias con JUnit 5 y Mockito para los servicios y controladores del backend.

La cobertura se midió utilizando JaCoCo.

| Microservicio | Cobertura de instrucciones |
|---|---:|
| Asistencias | 96 % |
| Trabajos | 94 % |

Las evidencias de cobertura se encuentran en el repositorio del backend.

## Credenciales de demostración

**Trabajador**

- RUT: `99.999.999-9`
- Contraseña: `42368`

**Administrador**

- RUT: `88.888.888-8`
- Contraseña: `12345`

Estas credenciales corresponden exclusivamente al prototipo académico y no constituyen un sistema de autenticación de producción.

## Instalación y ejecución

1. Clonar el repositorio Android.
2. Abrir el proyecto en Android Studio.
3. Sincronizar las dependencias con Gradle.
4. Configurar la dirección de red del backend cuando corresponda.
5. Iniciar los microservicios y PostgreSQL para utilizar las funciones sincronizadas.
6. Seleccionar un emulador o dispositivo Android.
7. Ejecutar la aplicación o instalar el APK firmado.

El APK de distribución se genera desde Android Studio mediante **Generate Signed App Bundle or APK**, seleccionando la variante **release**.

Por seguridad, el archivo de firma `.jks` y sus contraseñas no se incluyen en el repositorio.

## Recursos nativos

- **GPS:** ubicación del trabajador al registrar asistencia.
- **Cámara:** fotografías opcionales para informes.
- **Almacenamiento local:** persistencia mediante Room.
- **Animaciones:** elementos visuales desarrollados con Jetpack Compose.

## Limitaciones

ElectroTrack es un prototipo académico. Utiliza usuarios de demostración y no implementa autenticación empresarial ni despliegue público de los microservicios.

La conexión con el backend depende de la configuración de red y de que los servicios estén disponibles.

## Repositorios

**Aplicación Android:**  
https://github.com/BIngvab/dsy1105-electromechanics

**Backend:**  
https://github.com/BIngvab/ElectroTrack-Backend

## Asignatura

DSY1105 — Desarrollo de Aplicaciones Móviles  
Duoc UC