package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.google.gson.Gson
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager
import com.jerome.dusanter.youonlyneedcards.utils.MutableCircularList


class RetrieveGameInteractor {

    fun execute(listener: Listener, context: Context) {
        val gameString = SharedPreferencesManager.retrieveGame(context)
        if (!gameString.isNullOrBlank()) {
            mapFromJsonToGame(gameString)
            listener.onSuccess()
        } else {
            listener.onError()
        }
    }

    private fun mapFromJsonToGame(json: String) {
        val game = Gson().fromJson(json, Game::class.java)
        GameRepositoryImpl.currentStackTurn = 0
        GameRepositoryImpl.currentStateTurn = StateTurn.PreFlop
        GameRepositoryImpl.listPlayers = MutableCircularList(game.listPlayers)
        GameRepositoryImpl.settings = game.settings
        GameRepositoryImpl.timeRemainingBeforeIncreaseBlinds = game.timeRemainingBeforeIncreasingBlinds
    }

    interface Listener {
        fun onSuccess()
        fun onError()
    }
}
