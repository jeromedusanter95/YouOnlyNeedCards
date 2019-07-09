package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class EndGameInteractor {

    fun execute(listener: Listener) {
        listener.onEndGame(
            GameRepositoryImpl.getListPlayerEndGame(),
            GameRepositoryImpl.settings
        )
    }

    interface Listener {
        fun onEndGame(
            playerEndGameList: List<PlayerEndGame>?,
            settings: Settings
        )
    }
}