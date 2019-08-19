package com.jerome.dusanter.youonlyneedcards.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import com.jerome.dusanter.youonlyneedcards.di.ComponentManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


open class Application : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mFragmentInjector
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
