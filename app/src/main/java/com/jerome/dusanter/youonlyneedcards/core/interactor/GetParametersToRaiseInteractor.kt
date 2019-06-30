package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class GetParametersToRaiseInteractor {

    fun execute(listener: Listener) {
        listener.onSuccess(GameRepositoryImpl.settings.smallBlind * 2, GameRepositoryImpl.currentPlayer.stack)
    }

    interface Listener {
        fun onSuccess(bigBlind: Int, stackPlayer: Int)
    }
}
