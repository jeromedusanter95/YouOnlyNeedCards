package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.Settings
import javax.inject.Inject

class CheckIfGameOverInteractor @Inject internal constructor() {

    fun execute(listener: Listener) {
        listener.onSuccess(
            Response(
                isGameOver = Game.isGameOver(),
                playerEndGameList = if (Game.isGameOver()) Game.getListPlayerEndGame() else listOf(),
                settings = Game.settings
            )
        )
    }

    interface Listener {
        fun onSuccess(response: Response)
    }

    data class Response(
        val isGameOver: Boolean,
        val playerEndGameList: List<PlayerEndGame>,
        val settings: Settings
    )
}
