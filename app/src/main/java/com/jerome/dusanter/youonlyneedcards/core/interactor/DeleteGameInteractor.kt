package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager

class DeleteGameInteractor {

    fun execute(listener: Listener, context: Context) {
        SharedPreferencesManager.deleteGame(context)
        listener.onSuccess()
    }

    interface Listener {
        fun onSuccess()
    }
}
