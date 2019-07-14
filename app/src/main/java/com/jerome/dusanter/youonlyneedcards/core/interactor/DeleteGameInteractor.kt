package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager

class DeleteGameInteractor {

    fun execute(context: Context) {
        SharedPreferencesManager.deleteGame(context)
    }
}
