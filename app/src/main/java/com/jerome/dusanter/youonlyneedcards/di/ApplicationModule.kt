package com.jerome.dusanter.youonlyneedcards.di

import com.jerome.dusanter.youonlyneedcards.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule constructor(private val application: Application) {

    @Provides
    internal fun provideContext() = application.applicationContext
}
