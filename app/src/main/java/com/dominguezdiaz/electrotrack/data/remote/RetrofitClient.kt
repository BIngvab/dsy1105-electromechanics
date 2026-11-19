package com.dominguezdiaz.electrotrack.data.remote

import com.dominguezdiaz.electrotrack.network.TrabajoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.net.Proxy

object RetrofitClient {

    private val clienteHttp = OkHttpClient.Builder()
        .proxy(Proxy.NO_PROXY)
        .build()


    private const val BASE_URL_ASISTENCIAS = "http://10.0.2.2:8081/"
    private const val BASE_URL_TRABAJOS = "http://10.0.2.2:8082/"


    val asistenciaApi: AsistenciaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_ASISTENCIAS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AsistenciaApi::class.java)

    }


    val trabajoApi: TrabajoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_TRABAJOS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrabajoApi::class.java)

    }



}
