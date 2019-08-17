package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import javax.inject.Inject

class RebuyPlayerInteractor @Inject internal constructor() {

    fun execute(listener: Listener, playerId: String) {
        listener.onSuccess(Game.rebuyPlayer(playerId))
    }

    interface Listener {
        fun onSuccess(player: Player)
    }
}
