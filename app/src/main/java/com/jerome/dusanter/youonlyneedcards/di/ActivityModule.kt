package com.jerome.dusanter.youonlyneedcards.di

import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.ChooseWinnersDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.raise.RaiseDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsActivity
import com.jerome.dusanter.youonlyneedcards.app.welcome.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWelcomeActivity(): WelcomeActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivity(): SettingsActivity

    @ContributesAndroidInjector
    abstract fun contributeGameActivity(): GameActivity

    @ContributesAndroidInjector
    abstract fun contributeRaiseDialogFragment(): RaiseDialogFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseWinnersDialogFragment(): ChooseWinnersDialogFragment
}
