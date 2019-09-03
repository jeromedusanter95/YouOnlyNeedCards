package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndTurn
import com.jerome.dusanter.youonlyneedcards.core.Winner
import javax.inject.Inject

class DistributeStackInteractor @Inject internal constructor() {

    fun execute(winnerList: List<Winner>, listener: Listener) {
        listener.onSuccess(Response(Game.distributePotsToWinners(winnerList), Game.playersList))
    }

    interface Listener {
        fun onSuccess(response: Response)
    }

    data class Response(
        val playersEndTurnList: List<PlayerEndTurn>,
        val playersList: List<Player>
    )
}
