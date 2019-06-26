package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class PartTurn(
    private val stateTurn: StateTurn,
    private val listener: Listener
) {

    fun start() {
        GameRepositoryImpl.resetStatePlayerExceptFoldedAndAllIn()
        GameRepositoryImpl.resetStackBetPartTurn()
        if (stateTurn.name != StateTurn.PreFlop.name) {
            GameRepositoryImpl.moveToFirstPlayerAfterBigBlind()
        } else {
            GameRepositoryImpl.moveToFirstPlayerAvailable()
        }
        GameRepositoryImpl.getPossibleActions()
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
            listener.onEndPartTurn()
        } else {
            GameRepositoryImpl.moveToNextPlayerAvailable()
            GameRepositoryImpl.getPossibleActions()
        }
    }

    interface Listener {
        fun onEndPartTurn()
    }
}

