package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Game

class RebuyPlayerInteractor {

    fun execute(listener: Listener, playerId: String) {
        listener.onSuccess(Game.rebuyPlayer(playerId))
    }

    interface Listener {
        fun onSuccess(player: Player)
    }
}
