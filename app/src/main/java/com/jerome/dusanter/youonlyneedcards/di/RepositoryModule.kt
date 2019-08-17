package com.jerome.dusanter.youonlyneedcards.di

import com.jerome.dusanter.youonlyneedcards.core.boundaries.PreferencesRepository
import com.jerome.dusanter.youonlyneedcards.data.PreferencesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindPreferencesRepository(
        instance: PreferencesRepositoryImpl
    ): PreferencesRepository
}
