package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class GetPlayerListAndInitialStackInteractor {

    fun execute(listener: Listener) {
        listener.onSuccess(GameRepositoryImpl.listPlayers)
    }

    interface Listener {
        fun onSuccess(playerList: List<Player>)
    }
}
