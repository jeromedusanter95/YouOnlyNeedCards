package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager
import javax.inject.Inject

class DeleteGameInteractor @Inject internal constructor(
    val context: Context
) {
    fun execute() {
        GameRepositoryImpl.resetGame()
        SharedPreferencesManager.deleteGame(context)
    }
}
