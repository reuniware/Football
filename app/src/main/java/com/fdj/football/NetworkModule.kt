package com.fdj.football

import com.fdj.football.ui.main.FootballService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideFootballService(): FootballService {
        return FootballService.create()
    }
}