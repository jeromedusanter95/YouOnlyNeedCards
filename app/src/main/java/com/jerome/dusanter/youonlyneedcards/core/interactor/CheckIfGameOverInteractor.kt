package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.Game

class CheckIfGameOverInteractor {

    fun execute(listener: Listener) {

        if (Game.isGameOver()) {
            listener.onSuccess(
                true,
                Game.getListPlayerEndGame(),
                Game.settings
            )
        } else {
            listener.onSuccess(false, null, Game.settings)
        }

    }

    interface Listener {
        fun onSuccess(
            isGameOver: Boolean,
            playerEndGameList: List<PlayerEndGame>?,
            settings: Settings
        )
    }
}
