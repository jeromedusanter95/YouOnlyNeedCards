package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl


class Game : Turn.Listener {

    fun start() {
        if (GameRepositoryImpl.isIncreaseBlindsEnabled()) {
            GameRepositoryImpl.startTimerIncreaseBlinds()
        }
        startTurn()
    }

    private fun startTurn() {
        val turn = Turn(this)
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

    override fun onEndTurn() {
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