package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndTurn
import com.jerome.dusanter.youonlyneedcards.core.Winner
import com.jerome.dusanter.youonlyneedcards.core.Game

class DistributeStackInteractor {

    fun execute(winnerList: List<Winner>, listener: Listener) {
        listener.onSuccess(Game.distributePotsToWinners(winnerList), Game.listPlayers)
    }

    interface Listener {
        fun onSuccess(playerEndTurnList: List<PlayerEndTurn>, playerList: List<Player>)
    }
}
