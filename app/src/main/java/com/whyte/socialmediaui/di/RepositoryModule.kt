package com.whyte.socialmediaui.di

import com.whyte.socialmediaui.firbaseRealtimeDb.repository.RealtimeDbRepository
import com.whyte.socialmediaui.firbaseRealtimeDb.repository.RealtimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealtimeRepository(repo : RealtimeDbRepository): RealtimeRepository
}