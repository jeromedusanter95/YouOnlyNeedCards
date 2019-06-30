package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class PlayInteractor {

    fun execute(request: PlayRequest, listener: Listener) {
        when (request.actionPlayer) {
            ActionPlayer.AllIn.name -> {
                GameRepositoryImpl.allin()
            }
            ActionPlayer.Call.name -> {
                GameRepositoryImpl.call()
            }
            ActionPlayer.Check.name -> {
                GameRepositoryImpl.check()
            }
            ActionPlayer.Fold.name -> {
                GameRepositoryImpl.fold()
            }
            ActionPlayer.Raise.name -> {
                GameRepositoryImpl.raise(request.stackRaised)
            }
        }
        when {
            GameRepositoryImpl.isTurnOver() -> listener.endOfTurn()
            GameRepositoryImpl.isPartTurnOver() -> listener.endOfPartTurn()
            else -> {
                GameRepositoryImpl.moveToNextPlayerAvailable()
                listener.getPossiblesActions(
                    GameRepositoryImpl.getPossibleActions(),
                    GameRepositoryImpl.listPlayers,
                    GameRepositoryImpl.stackTurn
                )
            }
        }
    }

    interface Listener {
        fun getPossiblesActions(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int
        )

        fun endOfPartTurn()
        fun endOfTurn()
    }
}

data class PlayRequest(
    val actionPlayer: String,
    val stackRaised: Int = 0
)
