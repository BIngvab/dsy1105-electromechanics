package com.dominguezdiaz.electrotrack.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface AsistenciaApi {

    @GET("api/asistencias")
    suspend fun listarAsistencias(): List<AsistenciaDto>


    @GET("api/asistencias/{id}")
    suspend fun obtenerAsistencia(
        @Path("id") id: Long

    ): AsistenciaDto


    @POST("api/asistencias")
    suspend fun registrarAsistencia(
        @Body asistencia: AsistenciaDto

    ): AsistenciaDto


    @PUT("api/asistencias/{id}")
    suspend fun actualizarAsistencia(
        @Path("id") id: Long,
        @Body asistencia: AsistenciaDto

    ): AsistenciaDto


    @DELETE("api/asistencias/{id}")
    suspend fun eliminarAsistencia(
        @Path("id") id: Long

    )

}
