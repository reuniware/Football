package com.fdj.football.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: FootballRepository,
) : ViewModel() {
    suspend fun getAllLeagues() {
        repository.getAllLeagues()
    }
}

class FootballRepository @Inject constructor(private val service: FootballService) {
    suspend fun getAllLeagues() {
        val resp = service.getAllLeagues(query = "testquery")
    }
}

interface FootballService {

    @GET("/")
    suspend fun getAllLeagues(
        @Query("query") query: String,
    ):String

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): FootballService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FootballService::class.java)
        }
    }
}
