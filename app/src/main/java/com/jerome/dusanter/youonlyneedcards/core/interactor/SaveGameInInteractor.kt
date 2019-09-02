package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.google.gson.Gson
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.SavedParams
import com.jerome.dusanter.youonlyneedcards.core.boundaries.PreferencesRepository
import javax.inject.Inject

class SaveGameInInteractor @Inject internal constructor(
    private val preferencesRepository: PreferencesRepository
) {
    fun execute(timeRemainingBeforeIncreaseBlinds: Long) {
        preferencesRepository.saveGame(Gson().toJson(mapToGame(timeRemainingBeforeIncreaseBlinds)))
    }

    private fun mapToGame(timeRemainingBeforeIncreaseBlinds: Long): SavedParams {
        val arrayList = arrayListOf<Player>()
        Game.playersList.forEach {
            arrayList.add(it)
        }
        return SavedParams(
            arrayList,
            Game.settings,
            if (Game.settings.isIncreaseBlindsEnabled) {
                timeRemainingBeforeIncreaseBlinds
            } else {
                0
            }
        )
    }
}
