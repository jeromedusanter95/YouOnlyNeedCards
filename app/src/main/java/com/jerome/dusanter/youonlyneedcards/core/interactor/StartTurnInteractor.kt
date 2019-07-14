package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class StartTurnInteractor {
    fun execute(listener: Listener) {
        if (GameRepositoryImpl.settings.isIncreaseBlindsEnabled && GameRepositoryImpl.shouldIncreaseBlindNextTurn) {
            GameRepositoryImpl.increaseBlinds()
        }
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.initializeCurrentPlayerAfterBigBlind()
        listener.getPossibleActions(
            GameRepositoryImpl.getPossibleActions(),
            GameRepositoryImpl.listPlayers,
            GameRepositoryImpl.currentStackTurn,
            GameRepositoryImpl.currentStateTurn,
            if (GameRepositoryImpl.timeRemainingBeforeIncreaseBlinds > 0) {
                true
            } else {
                GameRepositoryImpl.shouldStartTimer
            },
            if (GameRepositoryImpl.timeRemainingBeforeIncreaseBlinds > 0) {
                GameRepositoryImpl.timeRemainingBeforeIncreaseBlinds
            } else {
                GameRepositoryImpl.settings.frequencyIncreasingBlind
            }
        )
        GameRepositoryImpl.timeRemainingBeforeIncreaseBlinds = 0
        GameRepositoryImpl.shouldStartTimer = false
    }

    interface Listener {
        fun getPossibleActions(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int,
            stateTurn: StateTurn,
            resetTimer: Boolean,
            durationBeforeIncreasingBlinds: Long
        )
    }
}
