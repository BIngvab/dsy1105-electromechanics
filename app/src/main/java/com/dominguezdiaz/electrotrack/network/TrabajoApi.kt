package com.dominguezdiaz.electrotrack.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.DELETE

interface TrabajoApi {

    @GET("api/trabajos")
    suspend fun listarTrabajos(): List<TrabajoDto>


    @GET("api/trabajos/trabajador/{trabajador}")
    suspend fun obtenerTrabajosPorTrabajador(
        @Path("trabajador") trabajador: String

    ): List<TrabajoDto>


    @POST("api/trabajos")
    suspend fun registrarTrabajo(
        @Body trabajo: TrabajoDto

    ): TrabajoDto


    @PUT("api/trabajos/{id}")
    suspend fun actualizarTrabajo(
        @Path("id") id: Long,
        @Body trabajo: TrabajoDto

    ): TrabajoDto


    @PATCH("api/trabajos/{id}/estado")
    suspend fun actualizarEstado(
        @Path("id") id: Long,
        @Query("estado") estado: String

    ): TrabajoDto


    @DELETE("api/trabajos/{id}")
    suspend fun eliminarTrabajo(
        @Path("id") id: Long

    )

}
