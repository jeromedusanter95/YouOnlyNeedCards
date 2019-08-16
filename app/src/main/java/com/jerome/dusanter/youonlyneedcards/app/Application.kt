package com.jerome.dusanter.youonlyneedcards.app

import android.app.Activity
import android.app.Application
import android.content.Context
import com.jerome.dusanter.youonlyneedcards.di.ComponentManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class Application : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupGraph()
    }

    private fun setupGraph() {
        val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        ComponentManager.init(preferences, this)
        ComponentManager.applicationComponent.inject(this)
    }

    companion object {
        protected const val PREFS_NAME = "default"
    }

}
