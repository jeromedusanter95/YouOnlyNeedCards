package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.Game
import javax.inject.Inject

class SaveSettingsInteractor @Inject internal constructor() {
    fun execute(settings: Settings, listener: Listener) {
        if (settings.isMoneyBetEnabled) {
            settings.ratioStackMoney = settings.stack / settings.money
        }
        Game.settings = settings
        listener.onSuccess()
    }

    interface Listener {
        fun onSuccess()
    }
}
