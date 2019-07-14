package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class StartGameInteractor {
    fun execute(listener: Listener) {
        if (GameRepositoryImpl.listPlayers.size > 1) {
            GameRepositoryImpl.initializeListWithGoodOrder()
            GameRepositoryImpl.initializeStateBlind()
            GameRepositoryImpl.initializeCurrentPlayerAfterBigBlind()
            listener.onSuccess(
                GameRepositoryImpl.getPossibleActions(),
                GameRepositoryImpl.listPlayers,
                GameRepositoryImpl.currentStackTurn,
                GameRepositoryImpl.isIncreaseBlindsEnabled(),
                GameRepositoryImpl.settings.frequencyIncreasingBlind
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


