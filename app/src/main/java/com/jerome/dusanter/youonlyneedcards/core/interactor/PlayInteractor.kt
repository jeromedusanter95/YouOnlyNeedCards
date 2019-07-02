package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.core.Winner
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
            GameRepositoryImpl.isTurnOver() -> {
                GameRepositoryImpl.endTurn()
                listener.getGameInformations(
                    GameRepositoryImpl.getPossibleActions(),
                    GameRepositoryImpl.listPlayers,
                    GameRepositoryImpl.currentStackTurn,
                    GameRepositoryImpl.currentStateTurn,
                    true
                )
            }
            GameRepositoryImpl.isPartTurnOver() -> {
                GameRepositoryImpl.endPartTurn()
                GameRepositoryImpl.moveToFirstPlayerAvailableFromSmallBlind()
                listener.getGameInformations(
                    GameRepositoryImpl.getPossibleActions(),
                    GameRepositoryImpl.listPlayers,
                    GameRepositoryImpl.currentStackTurn,
                    GameRepositoryImpl.currentStateTurn
                )
            }
            else -> {
                GameRepositoryImpl.moveToNextPlayerAvailable()
                listener.getGameInformations(
                    GameRepositoryImpl.getPossibleActions(),
                    GameRepositoryImpl.listPlayers,
                    GameRepositoryImpl.currentStackTurn,
                    GameRepositoryImpl.currentStateTurn
                )
            }
        }
    }

    interface Listener {
        fun getGameInformations(
            actionPlayerList: List<ActionPlayer>,
            playerList: List<Player>,
            stackTurn: Int,
            stateTurn: StateTurn,
            isEndTurn: Boolean = false,
            winnerList: List<Winner> = listOf()
        )
    }
}

data class PlayRequest(
    val actionPlayer: String,
    val stackRaised: Int = 0
)
