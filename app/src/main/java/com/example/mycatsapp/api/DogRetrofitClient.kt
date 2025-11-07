package com.example.mycatsapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Retrofit dedicado à API Dog CEO.
 * Base URL e formato da resposta estão documentados em https://dog.ceo/dog-api/.
 */
object DogRetrofitClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    private val okHttpClient = OkHttpClient.Builder().build()

    val dogApiService: DogApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogApiService::class.java)
}

