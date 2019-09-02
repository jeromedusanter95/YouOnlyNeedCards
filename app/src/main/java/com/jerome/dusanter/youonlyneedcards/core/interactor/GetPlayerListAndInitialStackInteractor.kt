package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import javax.inject.Inject

class GetPlayerListAndInitialStackInteractor @Inject internal constructor() {

    fun execute(listener: Listener) {
        listener.onSuccess(Game.playersList)
    }

    interface Listener {
        fun onSuccess(playerList: List<Player>)
    }
}
