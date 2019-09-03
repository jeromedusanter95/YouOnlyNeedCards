package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.*
import javax.inject.Inject

class PlayInteractor @Inject internal constructor() {

    fun execute(request: Request, listener: Listener) {
        when (request.actionPlayer) {
            ActionPlayer.AllIn -> {
                Game.allin()
            }
            ActionPlayer.Call -> {
                Game.call()
            }
            ActionPlayer.Check -> {
                Game.check()
            }
            ActionPlayer.Fold -> {
                Game.fold()
            }
            ActionPlayer.Raise -> {
                Game.raise(request.stackRaised)
            }
        }
        when {
            Game.isTurnOver() -> {
                val potList = Game.createAllPot()
                Game.endTurn()
                listener.onSuccess(
                    Response(
                        actionPlayerList = Game.getPossibleActions(),
                        currentPlayer = Game.currentPlayer,
                        playerList = Game.playersList,
                        stackTurn = Game.currentStackTurn,
                        stateTurn = Game.currentStateTurn,
                        isEndTurn = true,
                        potList = potList
                    )
                )
            }
            Game.isPartTurnOver() -> {
                Game.endPartTurn()
                if (Game.getNumberPlayersNotEliminated() == 2) {
                    Game.moveToBigBlindWhenRemainingOnlyTwoPlayers()
                } else {
                    Game.moveToFirstPlayerAvailableFromSmallBlind()
                }
                listener.onSuccess(
                    Response(
                        actionPlayerList = Game.getPossibleActions(),
                        currentPlayer = Game.currentPlayer,
                        playerList = Game.playersList,
                        stackTurn = Game.currentStackTurn,
                        stateTurn = Game.currentStateTurn
                    )
                )
            }
            else -> {
                Game.moveToNextPlayerAvailable()
                listener.onSuccess(
                    Response(
                        actionPlayerList = Game.getPossibleActions(),
                        currentPlayer = Game.currentPlayer,
                        playerList = Game.playersList,
                        stackTurn = Game.currentStackTurn,
                        stateTurn = Game.currentStateTurn
                    )
                )
            }
        }
    }

    interface Listener {
        fun onSuccess(response: Response)
    }

    data class Request(
        val actionPlayer: ActionPlayer,
        val stackRaised: Int = 0
    )

    data class Response(
        val actionPlayerList: List<ActionPlayer>,
        val currentPlayer: Player,
        val playerList: List<Player>,
        val stackTurn: Int,
        val stateTurn: StateTurn,
        val isEndTurn: Boolean = false,
        val potList: List<Pot> = listOf()
    )
}
