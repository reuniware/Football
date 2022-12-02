package com.fdj.football.ui.main

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FootballService {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(): FootballSearchResponse

    companion object {
        private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/50130162/"

        fun create(): FootballService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val gson: Gson? = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson!!))
                .build()
                .create(FootballService::class.java)
        }
    }
}