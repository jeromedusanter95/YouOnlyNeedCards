package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class CheckIfGameOverInteractor {

    fun execute(listener: Listener) {

        if (GameRepositoryImpl.isGameOver()) {
            listener.onSuccess(
                true,
                GameRepositoryImpl.getListPlayerEndGame(),
                GameRepositoryImpl.settings
            )
        } else {
            listener.onSuccess(false, null, GameRepositoryImpl.settings)
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
