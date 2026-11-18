package com.dominguezdiaz.electrotrack.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AsistenciaApi {

    @GET("api/asistencias")
    suspend fun listarAsistencias(): List<AsistenciaDto>


    @POST("api/asistencias")
    suspend fun registrarAsistencia(
        @Body asistencia: AsistenciaDto

    ): AsistenciaDto

}
