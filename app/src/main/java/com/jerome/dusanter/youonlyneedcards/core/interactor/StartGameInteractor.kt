package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class StartGameInteractor {
    fun execute(listener: Listener) {
        if (GameRepositoryImpl.isIncreaseBlindsEnabled()) {
            GameRepositoryImpl.startTimerIncreaseBlinds()
        }
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.initializeCurrentPlayerAfterBigBlind()
        listener.getPossibleActions(GameRepositoryImpl.getPossibleActions(), GameRepositoryImpl.listPlayers)
    }

    interface Listener {
        fun getPossibleActions(actionPlayerList: List<ActionPlayer>, playerList: List<Player>)
    }
}


