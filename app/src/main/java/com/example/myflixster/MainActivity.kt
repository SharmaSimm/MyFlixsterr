package com.example.myflixster

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myflixster.adapter.MovieAdapter
import com.example.myflixster.databinding.ActivityMainBinding
import com.example.myflixster.models.ConfigurationResponse
import com.example.myflixster.models.MovieResponse
import com.example.myflixster.network.ConfigurationRepository
import com.example.myflixster.network.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieRepository = MovieRepository()
    private val configurationRepository = ConfigurationRepository()
    private lateinit var baseImageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        // Fetch configuration and then movies
        fetchConfigurationAndMovies()
    }

    private fun fetchConfigurationAndMovies() {
        lifecycleScope.launch {
            try {
                val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

                // Fetch configuration
                val configurationResponse = withContext(Dispatchers.IO) {
                    configurationRepository.fetchConfiguration(apiKey)
                }

                if (configurationResponse != null) {
                    baseImageUrl = configurationResponse.images.base_url
                    fetchMovies(apiKey)
                } else {
                    Log.e("MainActivity", "ConfigurationResponse is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch configuration", e)
            }
        }
    }

    private fun fetchMovies(apiKey: String) {
        lifecycleScope.launch {
            try {
                val movieResponse = withContext(Dispatchers.IO) {
                    movieRepository.getNowPlayingMovies(apiKey)
                }

                if (movieResponse != null) {
                    val movies = movieResponse.results ?: emptyList()
                    Log.d("MainActivity", "Movie list size: ${movies.size}")
                    val adapter = MovieAdapter(movies, baseImageUrl)
                    binding.rvMovies.adapter = adapter
                } else {
                    Log.e("MainActivity", "MovieResponse is null")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Failed to fetch movies", e)
            }
        }
    }
}
