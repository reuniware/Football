package com.fdj.football.ui.main

import com.google.gson.annotations.SerializedName

data class League(
    @field:SerializedName("idLeague") val id: String,
    @field:SerializedName("strLeague") val league: String,
    @field:SerializedName("strSport") val sport: String,
    @field:SerializedName("strLeagueAlternate") val leagueAlternate: String?,
)