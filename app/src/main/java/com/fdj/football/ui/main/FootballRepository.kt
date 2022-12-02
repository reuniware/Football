package com.fdj.football.ui.main

import javax.inject.Inject

class FootballRepository @Inject constructor(private val service: FootballService) {
    suspend fun getAllLeagues(): ArrayList<League> {
        val resp = service.getAllLeagues()
        val listOfLeagues = ArrayList<League>()
        resp.results.forEach {
            listOfLeagues.add(League(it.id, it.league, it.sport, it.leagueAlternate))
        }
        return listOfLeagues
    }
}