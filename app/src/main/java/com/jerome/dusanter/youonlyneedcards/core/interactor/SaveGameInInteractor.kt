package com.jerome.dusanter.youonlyneedcards.core.interactor

import android.content.Context
import com.google.gson.Gson
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import com.jerome.dusanter.youonlyneedcards.data.SharedPreferencesManager

class SaveGameInInteractor {

    fun execute(context: Context, timeRemainingBeforeIncreaseBlinds: Long) {
        SharedPreferencesManager.saveGame(
            context,
            Gson().toJson(mapToGame(timeRemainingBeforeIncreaseBlinds))
        )
    }

    private fun mapToGame(timeRemainingBeforeIncreaseBlinds: Long): Game {
        val arrayList = arrayListOf<Player>()
        GameRepositoryImpl.listPlayers.forEach {
            arrayList.add(it)
        }
        return Game(
            arrayList,
            GameRepositoryImpl.settings,
            if (GameRepositoryImpl.settings.isIncreaseBlindsEnabled) {
                timeRemainingBeforeIncreaseBlinds
            } else {
                0
            }
        )
    }
}
