package com.fdj.football.ui.main

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
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
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 5,
        @Query("client_id") clientId: String = "null"
    ):FootballSearchResponse

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

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

data class FootballSearchResponse(
    @field:SerializedName("results") val results: List<UnsplashPhoto>,
    @field:SerializedName("total_pages") val totalPages: Int
)

data class UnsplashPhoto(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("urls") val urls: UnsplashPhotoUrls,
    @field:SerializedName("user") val user: UnsplashUser
)

data class UnsplashPhotoUrls(
    @field:SerializedName("small") val small: String
)

data class UnsplashUser(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("username") val username: String
) {
    val attributionUrl: String
        get() {
            return "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
        }
}



