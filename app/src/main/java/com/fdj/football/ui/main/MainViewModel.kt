package com.fdj.football.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    var leaguesLiveData = MutableLiveData<ArrayList<League>>()

    suspend fun getAllLeagues() {
        val leagues = repository.getAllLeagues()
        leaguesLiveData = MutableLiveData(leagues)
    }
}

class FootballRepository @Inject constructor(private val service: FootballService) {
    suspend fun getAllLeagues(): ArrayList<League> {
        val resp = service.getAllLeagues(/*query = "testquery"*/)
        Log.d("resp", resp.toString())
        val listOfLeagues = ArrayList<League>()
        resp.results.forEach {
            listOfLeagues.add(League(it.id,it.league, it.sport, it.leagueAlternate))
        }
        return listOfLeagues
    }
}

interface FootballService {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(
//        @Query("query") query: String,
//        @Query("page") page: Int = 1,
//        @Query("per_page") perPage: Int = 5,
//        @Query("client_id") clientId: String = "null"
    ):FootballSearchResponse

    companion object {
        //private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/50130162/all_leagues.php"
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

data class FootballSearchResponse(
    @field:SerializedName("leagues") val results: List<League>,
)

data class League(
    @field:SerializedName("idLeague") val id: String,
    @field:SerializedName("strLeague") val league: String,
    @field:SerializedName("strSport") val sport: String,
    @field:SerializedName("strLeagueAlternate") val leagueAlternate: String?,
)




