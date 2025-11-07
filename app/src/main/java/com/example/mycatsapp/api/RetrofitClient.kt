package com.example.mycatsapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que cria e fornece uma instância do Retrofit
 * configurada para fazer chamadas à API do The Cat API
 */
object RetrofitClient {
    // URL base da API
    private const val BASE_URL = "https://api.thecatapi.com/"
    
    // API Key (se necessário - The Cat API não requer, mas outras APIs sim)
    // Para usar, descomente e adicione sua chave:
    // private const val API_KEY = "sua-api-key-aqui"
    
    /**
     * Interceptor que adiciona headers em todas as requisições
     * Isso é executado automaticamente antes de cada chamada HTTP
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Cria uma nova requisição com headers adicionais
        val newRequest = originalRequest.newBuilder()
            // Adiciona header de API Key (se necessário)
            // .addHeader("x-api-key", API_KEY)
            
            // Headers comuns que muitas APIs precisam:
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            
            // Exemplo: Header de autenticação Bearer Token
            // .addHeader("Authorization", "Bearer $TOKEN")
            
            // Exemplo: Header customizado
            // .addHeader("X-Custom-Header", "valor-customizado")
            
            .build()
        
        // Continua com a requisição modificada
        chain.proceed(newRequest)
    }
    
    /**
     * Cliente HTTP configurado com o interceptor
     * O OkHttpClient é o que realmente faz as chamadas HTTP
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)  // Adiciona o interceptor de headers
        // .addInterceptor(loggingInterceptor)  // Para logs (opcional)
        .build()
    
    /**
     * Instância do Retrofit configurada com:
     * - URL base
     * - Conversor Gson (JSON → Kotlin)
     * - Cliente HTTP com headers personalizados
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)  // Define a URL base
        .client(okHttpClient)  // Usa o cliente HTTP com headers
        .addConverterFactory(GsonConverterFactory.create())  // Converte JSON para objetos Kotlin
        .build()
    
    // Instância do serviço da API (criada a partir do Retrofit)
    val catApiService: CatApiService = retrofit.create(CatApiService::class.java)
}

