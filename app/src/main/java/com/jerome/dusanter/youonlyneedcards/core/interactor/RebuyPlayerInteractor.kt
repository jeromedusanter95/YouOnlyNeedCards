package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class RebuyPlayerInteractor {

    fun execute(listener: Listener, playerId: String) {
        listener.onSuccess(GameRepositoryImpl.rebuyPlayer(playerId))
    }

    interface Listener {
        fun onSuccess(player: Player)
    }
}
