package com.fdj.football.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: FootballRepository,
) : ViewModel() {

    var leaguesLiveData = MutableLiveData<ArrayList<League>>()
    var currentSearchText: String = ""
    var initialLeagues = ArrayList<League>()

    private var allLeaguesDownloaded = false
    suspend fun getAllLeagues() {
        if (allLeaguesDownloaded)
            return
        val leagues = repository.getAllLeagues()
        if (leagues.size>0) {
            allLeaguesDownloaded = true
        }
        CoroutineScope(Dispatchers.Main).launch {
            leaguesLiveData.postValue(leagues)
        }
    }
}




