package com.dominguezdiaz.electrotrack.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ClimaApi {

    @GET("v1/forecast")
    suspend fun obtenerClima(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("current") variables: String =
            "temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m"

    ): ClimaDto

}


object ClimaRetrofitClient {

    val climaApi: ClimaApi by lazy {

        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClimaApi::class.java)

    }

}
