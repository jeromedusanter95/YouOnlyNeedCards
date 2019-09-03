package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import javax.inject.Inject

class EndGameInteractor @Inject internal constructor() {

    fun execute(listener: Listener) {
        listener.onEndGame(
            Response(
                playerEndGameList = Game.getListPlayerEndGame(),
                settings = Game.settings
            )
        )
    }

    interface Listener {
        fun onEndGame(response: Response)
    }

    data class Response(
        val playerEndGameList: List<PlayerEndGame>,
        val settings: Settings
    )
}
