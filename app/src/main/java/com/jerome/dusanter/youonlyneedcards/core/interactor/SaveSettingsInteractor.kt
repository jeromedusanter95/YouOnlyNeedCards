package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class SaveSettingsInteractor {

    fun execute(settings: Settings, listener: Listener) {
        GameRepositoryImpl.settings = settings
        listener.onSuccess()
    }

    interface Listener {
        fun onSuccess()
    }
}