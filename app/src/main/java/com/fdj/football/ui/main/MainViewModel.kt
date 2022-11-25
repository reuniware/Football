package com.fdj.football.ui.main

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor(
    var repository: FootballRepository,
    var listOfLeagues: ArrayList<String> = ArrayList<String>()
) : ViewModel() {
    fun getAllLeagues() {
        listOfLeagues = repository.getAllLeagues()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object FootballRepository {
    @Provides
//    @Singleton
    fun getAllLeagues(): ArrayList<String> {
        return ArrayList<String>()
    }
}
