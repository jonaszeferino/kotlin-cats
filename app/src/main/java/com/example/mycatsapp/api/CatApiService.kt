package com.example.mycatsapp.api

import com.example.mycatsapp.data.CatApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface que define os endpoints da API do The Cat API
 * O Retrofit usa esta interface para criar as chamadas HTTP
 */
interface CatApiService {
    
    /**
     * Busca uma imagem de gato pelo ID
     * @param imageId O ID da imagem (ex: "0XYvRd7oD")
     * @return CatApiResponse com os dados do gato
     */
    @GET("v1/images/{imageId}")
    suspend fun getCatImage(@Path("imageId") imageId: String): CatApiResponse
    
    /**
     * Busca imagens aleatórias de gatos
     * @param limit Número de imagens a retornar (padrão: 1)
     * @return Lista de CatApiResponse com os dados dos gatos
     */
    @GET("v1/images/search")
    suspend fun searchCatImages(@Query("limit") limit: Int = 1): List<CatApiResponse>
}

