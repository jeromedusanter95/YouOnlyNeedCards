package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.*
import javax.inject.Inject

class PlayInteractor @Inject internal constructor() {

    fun execute(request: PlayRequest, listener: Listener) {
        when (request.actionPlayer) {
            ActionPlayer.AllIn.name -> {
                Game.allin()
            }
            ActionPlayer.Call.name -> {
                Game.call()
            }
            ActionPlayer.Check.name -> {
                Game.check()
            }
            ActionPlayer.Fold.name -> {
                Game.fold()
            }
            ActionPlayer.Raise.name -> {
                Game.raise(request.stackRaised)
            }
        }
        when {
            Game.isTurnOver() -> {
                val potList = Game.createAllPot()
                Game.endTurn()
                listener.getGameInformations(
                    Game.getPossibleActions(),
                    Game.listPlayers,
                    Game.currentStackTurn,
                    Game.currentStateTurn,
                    true,
                    potList
                )
            }
            Game.isPartTurnOver() -> {
                Game.endPartTurn()
                if (Game.getNumberPlayersNotEliminated() == 2) {
                    Game.moveToBigBlindWhenRemainingOnlyTwoPlayers()
                } else {
                    Game.moveToFirstPlayerAvailableFromSmallBlind()
                }
                listener.getGameInformations(
                    Game.getPossibleActions(),
                    Game.listPlayers,
                    Game.currentStackTurn,
                    Game.currentStateTurn
                )
            }
            else -> {
                Game.moveToNextPlayerAvailable()
                listener.getGameInformations(
                    Game.getPossibleActions(),
                    Game.listPlayers,
                    Game.currentStackTurn,
                    Game.currentStateTurn
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
            potList: List<Pot> = listOf()
        )
    }
}

data class PlayRequest(
    val actionPlayer: String,
    val stackRaised: Int = 0
)
