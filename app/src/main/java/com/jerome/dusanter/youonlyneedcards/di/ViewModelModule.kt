package com.jerome.dusanter.youonlyneedcards.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jerome.dusanter.youonlyneedcards.app.game.GameViewModel
import com.jerome.dusanter.youonlyneedcards.app.game.raise.RaiseViewModel
import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsViewModel
import com.jerome.dusanter.youonlyneedcards.app.welcome.WelcomeViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    protected abstract fun welcomeViewModel(welcomeViewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    protected abstract fun settingsViewModel(settingsViewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    protected abstract fun gameViewModel(gameViewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RaiseViewModel::class)
    protected abstract fun raiseDialogViewModel(raiseViewModel: RaiseViewModel): ViewModel
}
