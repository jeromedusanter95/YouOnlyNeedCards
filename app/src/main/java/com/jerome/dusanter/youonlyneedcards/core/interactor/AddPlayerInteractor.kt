package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class AddPlayerInteractor {
    fun execute(id: String, name: String, listener: Listener) {
        val player = GameRepositoryImpl.addPlayer(id, name)
        listener.onSuccess(player = player)
    }

    interface Listener {
        fun onSuccess(player: Player)
    }
}