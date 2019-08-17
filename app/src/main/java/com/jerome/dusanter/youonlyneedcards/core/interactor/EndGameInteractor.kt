package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import javax.inject.Inject

class EndGameInteractor@Inject internal constructor() {

    fun execute(listener: Listener) {
        listener.onEndGame(
            Game.getListPlayerEndGame(),
            Game.settings
        )
    }

    interface Listener {
        fun onEndGame(
            playerEndGameList: List<PlayerEndGame>?,
            settings: Settings
        )
    }
}
