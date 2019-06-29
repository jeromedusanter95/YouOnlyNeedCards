/*
package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl


class Game {

    fun start() {
        if (GameRepositoryImpl.isIncreaseBlindsEnabled()) {
            GameRepositoryImpl.startTimerIncreaseBlinds()
        }
        startTurn()
    }

    private fun startTurn() {
        val turn = StateTurn.Turn(this)
        turn.start()
    }

    private fun end() {
        if (GameRepositoryImpl.isMoneyBetEnabled()) {
            GameRepositoryImpl.distributeMoneyToWinners()
        }
    }

    fun save() {
        GameRepositoryImpl.save()
    }

    fun recave() {
        GameRepositoryImpl.recave()
    }

    fun onEndTurn() {
        if (GameRepositoryImpl.isGameOver()) {
            end()
        } else {
            if (GameRepositoryImpl.shouldIncreaseBlinds()
            ) {
                GameRepositoryImpl.increaseBlinds()
                GameRepositoryImpl.startTimerIncreaseBlinds()
            }
            startTurn()
        }
    }
}
*/
