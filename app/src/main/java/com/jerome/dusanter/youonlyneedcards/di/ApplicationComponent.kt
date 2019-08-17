package com.jerome.dusanter.youonlyneedcards.di

import com.jerome.dusanter.youonlyneedcards.app.Application
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        ActivityModule::class,
        ApplicationModule::class,
        PreferencesModule::class,
        RepositoryModule::class,
        AndroidSupportInjectionModule::class]
)

interface ApplicationComponent {
    fun inject(application: Application)
}
