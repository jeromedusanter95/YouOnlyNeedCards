package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game

class GetParametersToRaiseInteractor {

    fun execute(listener: Listener) {
        listener.onSuccess(Game.settings.smallBlind * 2, Game.currentPlayer.stack)
    }

    interface Listener {
        fun onSuccess(bigBlind: Int, stackPlayer: Int)
    }
}
