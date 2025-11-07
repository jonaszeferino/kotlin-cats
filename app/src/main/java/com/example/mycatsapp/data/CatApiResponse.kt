package com.example.mycatsapp.data

import com.google.gson.annotations.SerializedName

// Classe principal que representa a resposta completa da API
data class CatApiResponse(
    val id: String,
    val url: String,
    val breeds: List<Breed>? = null, // Pode ser null ou vazio na resposta do search
    val width: Int? = null, // Pode ser null em algumas respostas
    val height: Int? = null // Pode ser null em algumas respostas
)

// Classe que representa informações sobre a raça do gato
data class Breed(
    val weight: Weight? = null, // Pode ser null
    val id: String? = null,
    val name: String? = null,
    @SerializedName("cfa_url") val cfaUrl: String? = null,
    @SerializedName("vetstreet_url") val vetstreetUrl: String? = null,
    @SerializedName("vcahospitals_url") val vcahospitalsUrl: String? = null,
    val temperament: String? = null,
    val origin: String? = null,
    @SerializedName("country_codes") val countryCodes: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    val description: String? = null,
    @SerializedName("life_span") val lifeSpan: String? = null,
    val indoor: Int? = null,
    val lap: Int? = null,
    @SerializedName("alt_names") val altNames: String? = null,
    val adaptability: Int? = null,
    @SerializedName("affection_level") val affectionLevel: Int? = null,
    @SerializedName("child_friendly") val childFriendly: Int? = null,
    @SerializedName("dog_friendly") val dogFriendly: Int? = null,
    @SerializedName("energy_level") val energyLevel: Int? = null,
    val grooming: Int? = null,
    @SerializedName("health_issues") val healthIssues: Int? = null,
    val intelligence: Int? = null,
    @SerializedName("shedding_level") val sheddingLevel: Int? = null,
    @SerializedName("social_needs") val socialNeeds: Int? = null,
    @SerializedName("stranger_friendly") val strangerFriendly: Int? = null,
    val vocalisation: Int? = null,
    val experimental: Int? = null,
    val hairless: Int? = null,
    val natural: Int? = null,
    val rare: Int? = null,
    val rex: Int? = null,
    @SerializedName("suppressed_tail") val suppressedTail: Int? = null,
    @SerializedName("short_legs") val shortLegs: Int? = null,
    @SerializedName("wikipedia_url") val wikipediaUrl: String? = null,
    val hypoallergenic: Int? = null,
    @SerializedName("reference_image_id") val referenceImageId: String? = null
)

// Classe que representa o peso do gato (imperial e métrico)
data class Weight(
    val imperial: String? = null,
    val metric: String? = null
)

