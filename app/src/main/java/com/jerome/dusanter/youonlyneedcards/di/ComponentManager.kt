package com.jerome.dusanter.youonlyneedcards.di

import android.content.SharedPreferences
import com.jerome.dusanter.youonlyneedcards.app.Application

object ComponentManager {

    internal lateinit var applicationComponent: ApplicationComponent

    fun init(preferences: SharedPreferences, application: Application) {
        val preferencesModule = PreferencesModule(preferences)
        val applicationModule = ApplicationModule(application)
        initApplicationComponent(applicationModule, preferencesModule)
    }

    private fun initApplicationComponent(applicationModule: ApplicationModule, preferencesModule: PreferencesModule) {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(applicationModule)
            .preferencesModule(preferencesModule)
            .build()
    }
}
