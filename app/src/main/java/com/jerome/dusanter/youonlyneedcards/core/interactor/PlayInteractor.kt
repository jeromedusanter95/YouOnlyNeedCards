package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class PlayInteractor {

    fun execute(actionPlayer: String, listener: Listener) {
        when (actionPlayer) {
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
                GameRepositoryImpl.raise()
            }
        }
        when {
            GameRepositoryImpl.isTurnOver() -> listener.endOfTurn()
            GameRepositoryImpl.isPartTurnOver() -> listener.endOfPartTurn()
            else -> {
                GameRepositoryImpl.moveToNextPlayerAvailable()
                listener.getPossiblesAction(GameRepositoryImpl.getPossibleActions())
            }
        }
    }

    interface Listener {
        fun getPossiblesAction(actionPlayerList: List<ActionPlayer>)
        fun endOfPartTurn()
        fun endOfTurn()
    }
}
