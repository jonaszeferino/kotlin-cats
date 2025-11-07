package com.example.mycatsapp.api

import com.example.mycatsapp.data.DogApiResponse
import retrofit2.http.GET

/**
 * Endpoints disponíveis na API Dog CEO.
 * Documentação: https://dog.ceo/dog-api/
 */
interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogApiResponse
}

