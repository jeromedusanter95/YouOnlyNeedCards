package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class StartGameInteractor {
    fun execute(listener: Listener) {
        GameRepositoryImpl.initializeListWithGoodOrder()
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.initializeCurrentPlayerAfterBigBlind()
        listener.getPossibleActions(
            GameRepositoryImpl.getPossibleActions(),
            GameRepositoryImpl.listPlayers,
            GameRepositoryImpl.currentStackTurn,
            GameRepositoryImpl.isIncreaseBlindsEnabled(),
            GameRepositoryImpl.settings.frequencyIncreasingBlind
        )
    }

    interface Listener {
        fun getPossibleActions(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int,
            resetTimer: Boolean,
            durationBeforeIncreasingBlind: Long
        )
    }
}


