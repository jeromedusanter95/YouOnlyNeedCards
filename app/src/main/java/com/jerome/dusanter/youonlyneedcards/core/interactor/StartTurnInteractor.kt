package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class StartTurnInteractor() {
    fun execute(listener: Listener) {
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.initializeCurrentPlayerAfterBigBlind()
        listener.getPossibleActions(
            GameRepositoryImpl.getPossibleActions(),
            GameRepositoryImpl.listPlayers,
            GameRepositoryImpl.currentStackTurn,
            GameRepositoryImpl.currentStateTurn
        )
    }

    interface Listener {
        fun getPossibleActions(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int,
            stateTurn: StateTurn
        )
    }
}
