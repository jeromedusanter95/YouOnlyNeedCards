package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import javax.inject.Inject

class StartGameInteractor @Inject internal constructor() {
    fun execute(listener: Listener) {
        if (Game.playersList.size > 1) {
            Game.initializeListWithGoodOrder()
            Game.initializeStateBlind()
            Game.initializeCurrentPlayerAfterBigBlind()
            listener.onSuccess(
                Response(
                    actionPlayerList = Game.getPossibleActions(),
                    currentPlayer = Game.currentPlayer,
                    playersList = Game.playersList,
                    stackTurn = Game.currentStackTurn,
                    resetTimer = Game.isIncreaseBlindsEnabled(),
                    durationBeforeIncreasingBlinds = Game.settings.frequencyIncreasingBlind
                )
            )
        } else {
            listener.onError()
        }
    }

    interface Listener {
        fun onSuccess(response: Response)
        fun onError()
    }

    data class Response(
        val actionPlayerList: List<ActionPlayer>,
        val currentPlayer: Player,
        val playersList: List<Player>,
        val stackTurn: Int,
        val resetTimer: Boolean,
        val durationBeforeIncreasingBlinds: Long
    )
}


