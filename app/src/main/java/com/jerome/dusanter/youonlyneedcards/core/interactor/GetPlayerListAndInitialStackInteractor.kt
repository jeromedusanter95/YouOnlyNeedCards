package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Game

class GetPlayerListAndInitialStackInteractor {

    fun execute(listener: Listener) {
        listener.onSuccess(Game.listPlayers)
    }

    interface Listener {
        fun onSuccess(playerList: List<Player>)
    }
}
