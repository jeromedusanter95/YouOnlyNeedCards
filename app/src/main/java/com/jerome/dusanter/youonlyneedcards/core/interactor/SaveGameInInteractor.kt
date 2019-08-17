package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.google.gson.Gson
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.boundaries.PreferencesRepository
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import javax.inject.Inject

class SaveGameInInteractor @Inject internal constructor(
    private val preferencesRepository: PreferencesRepository
) {
    fun execute(timeRemainingBeforeIncreaseBlinds: Long) {
        preferencesRepository.saveGame(Gson().toJson(mapToGame(timeRemainingBeforeIncreaseBlinds)))
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
