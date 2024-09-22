package com.example.myflixster.api

import com.example.myflixster.models.MovieResponse
import com.example.myflixster.models.ConfigurationResponse // Ensure this import is correct
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ConfigurationResponse
}
