package com.example.mycatsapp.repository

import com.example.mycatsapp.api.RetrofitClient
import com.example.mycatsapp.api.DogRetrofitClient
import com.example.mycatsapp.data.CatApiResponse
import com.example.mycatsapp.data.DogApiResponse

/**
 * REPOSITORY (Model Layer)
 * 
 * Responsável por:
 * - Acessar dados (API, banco de dados, etc.)
 * - Centralizar lógica de acesso a dados
 * - Pode combinar múltiplas fontes de dados
 */
class CatRepository {
    
    /**
     * Busca imagens aleatórias de gatos
     * @param limit Número de imagens a retornar
     * @return Lista de CatApiResponse
     */
    suspend fun searchCatImages(limit: Int = 1): List<CatApiResponse> =
        RetrofitClient.catApiService.searchCatImages(limit = limit)
    
    /**
     * Busca uma imagem específica por ID
     */
    suspend fun getCatImageById(imageId: String): CatApiResponse {
        return RetrofitClient.catApiService.getCatImage(imageId)
    }

    /**
     * Busca uma imagem aleatória de cachorro.
     */
    suspend fun getRandomDogImage(): DogApiResponse =
        DogRetrofitClient.dogApiService.getRandomDogImage()
}

