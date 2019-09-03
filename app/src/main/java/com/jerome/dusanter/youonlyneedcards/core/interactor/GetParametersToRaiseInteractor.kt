package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import javax.inject.Inject

class GetParametersToRaiseInteractor @Inject internal constructor() {

    fun execute(listener: Listener) {
        listener.onSuccess(
            Response(
                bigBlind = Game.settings.smallBlind * 2,
                stackPlayer = Game.currentPlayer.stack
            )
        )
    }

    interface Listener {
        fun onSuccess(response: Response)
    }

    data class Response(
        val bigBlind: Int,
        val stackPlayer: Int
    )
}
