package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.google.gson.Gson
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager

class SaveGameInSharedPreferencesInteractor {

    fun execute(listener: Listener, context: Context) {
        SharedPreferencesManager.saveGame(context, Gson().toJson(mapToGame()))
        listener.onSuccess()
    }

    private fun mapToGame(): Game {
        return Game(
            GameRepositoryImpl.listPlayers,
            GameRepositoryImpl.settings
        )
    }

    interface Listener {
        fun onSuccess()
    }
}
