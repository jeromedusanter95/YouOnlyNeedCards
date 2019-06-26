package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game

class StartGameInteractor {

    fun execute(listener: Listener) {
        val game = Game()
        game.start()
    }

    interface Listener {
        fun onSuccess(game: Game)
    }
}