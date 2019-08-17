package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import javax.inject.Inject

class StartTurnInteractor @Inject internal constructor() {
    fun execute(listener: Listener) {
        if (Game.settings.isIncreaseBlindsEnabled && Game.shouldIncreaseBlindNextTurn) {
            Game.increaseBlinds()
        }
        Game.initializeStateBlind()
        Game.initializeCurrentPlayerAfterBigBlind()
        listener.getPossibleActions(
            Game.getPossibleActions(),
            Game.listPlayers,
            Game.currentStackTurn,
            Game.currentStateTurn,
            if (Game.timeRemainingBeforeIncreaseBlinds > 0) {
                true
            } else {
                Game.shouldStartTimer
            },
            if (Game.timeRemainingBeforeIncreaseBlinds > 0) {
                Game.timeRemainingBeforeIncreaseBlinds
            } else {
                Game.settings.frequencyIncreasingBlind
            }
        )
        Game.timeRemainingBeforeIncreaseBlinds = 0
        Game.shouldStartTimer = false
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
