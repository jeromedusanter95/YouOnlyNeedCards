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
        val resetTimer = if (Game.timeRemainingBeforeIncreaseBlinds > 0) {
            true
        } else {
            Game.shouldStartTimer
        }
        val durationBeforeIncreasingBlinds = if (Game.timeRemainingBeforeIncreaseBlinds > 0) {
            Game.timeRemainingBeforeIncreaseBlinds
        } else {
            Game.settings.frequencyIncreasingBlind
        }
        listener.onSuccess(
            Response(
                actionPlayerList = Game.getPossibleActions(),
                playerList = Game.playersList,
                stackTurn = Game.currentStackTurn,
                stateTurn = Game.currentStateTurn,
                resetTimer = resetTimer,
                durationBeforeIncreasingBlinds = durationBeforeIncreasingBlinds
            )
        )
        Game.timeRemainingBeforeIncreaseBlinds = 0
        Game.shouldStartTimer = false
    }

    interface Listener {
        fun onSuccess(response: Response)
    }

    data class Response(
        val actionPlayerList: List<ActionPlayer>,
        val playerList: List<Player>,
        val stackTurn: Int,
        val stateTurn: StateTurn,
        val resetTimer: Boolean,
        val durationBeforeIncreasingBlinds: Long
    )
}
