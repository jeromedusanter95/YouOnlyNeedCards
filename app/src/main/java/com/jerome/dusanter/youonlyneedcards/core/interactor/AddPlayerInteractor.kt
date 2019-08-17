package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import javax.inject.Inject

class AddPlayerInteractor @Inject internal constructor() {
    fun execute(id: String, name: String, listener: Listener) {
        val player = Game.addPlayer(id, name)
        listener.onSuccess(player = player)
    }

    interface Listener {
        fun onSuccess(player: Player)
    }
}
