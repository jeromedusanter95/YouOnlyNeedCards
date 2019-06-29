/*
package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class PartTurn {

    private var isPartTurnOver: Boolean = false

    fun start() {
        GameRepositoryImpl.resetStatePlayerExceptFoldedAndAllIn()
        GameRepositoryImpl.resetStackBetPartTurn()
        if (stateTurn == StateTurn.PreFlop) {
            GameRepositoryImpl.moveToFirstPlayerAfterBigBlind()
        } else {
            GameRepositoryImpl.moveToFirstPlayerAvailable()
        }
        listener.getPossibleActions(GameRepositoryImpl.getPossibleActions())
    }

    fun handleActionPlayer(actionPlayer: ActionPlayer, stackRaised: Int) {
        when (actionPlayer) {
            ActionPlayer.AllIn -> {
                GameRepositoryImpl.allin()
            }
            ActionPlayer.Call -> {
                GameRepositoryImpl.call()
            }
            ActionPlayer.Check -> {
                GameRepositoryImpl.check()
            }
            ActionPlayer.Fold -> {
                GameRepositoryImpl.fold()
            }
            ActionPlayer.Raise -> {
                GameRepositoryImpl.raise(stackRaised = stackRaised)
            }
        }
        if (GameRepositoryImpl.isPartTurnOver()) {
            isPartTurnOver = true
        } else {
            GameRepositoryImpl.moveToNextPlayerAvailable()
            listener.getPossibleActions(GameRepositoryImpl.getPossibleActions())
        }
    }
}

*/
