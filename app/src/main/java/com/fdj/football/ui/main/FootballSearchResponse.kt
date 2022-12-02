package com.fdj.football.ui.main

import com.google.gson.annotations.SerializedName

data class FootballSearchResponse(
    @field:SerializedName("leagues") val results: List<League>,
)