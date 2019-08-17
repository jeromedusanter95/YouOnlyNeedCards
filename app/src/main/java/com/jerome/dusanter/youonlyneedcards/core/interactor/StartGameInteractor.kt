package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Game

class StartGameInteractor {
    fun execute(listener: Listener) {
        if (Game.listPlayers.size > 1) {
            Game.initializeListWithGoodOrder()
            Game.initializeStateBlind()
            Game.initializeCurrentPlayerAfterBigBlind()
            listener.onSuccess(
                Game.getPossibleActions(),
                Game.listPlayers,
                Game.currentStackTurn,
                Game.isIncreaseBlindsEnabled(),
                Game.settings.frequencyIncreasingBlind
            )
        } else {
            listener.onError()
        }
    }

    interface Listener {
        fun onSuccess(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int,
            resetTimer: Boolean,
            durationBeforeIncreasingBlind: Long
        )

        fun onError()
    }
}


