// TMDBService.kt
package com.example.myflixster.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ConfigurationResponse
}

// Data classes to parse the configuration response
data class ConfigurationResponse(
    val images: Images
)

data class Images(
    val base_url: String,
    val poster_sizes: List<String>
)
