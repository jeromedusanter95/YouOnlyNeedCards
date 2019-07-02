/*
package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class Turn(private val listener: GameListener) {

    lateinit var currentPartTurn: PartTurn

    fun start() {
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.resetStatePlayer()
        GameRepositoryImpl.resetStackBetTurn()
        startPartTurn(StateTurn.PreFlop)
    }

    private fun startPartTurn(currentStateTurn: StateTurn) {
        currentPartTurn = PartTurn(currentStateTurn, listener)
        currentPartTurn.start()
    }

    private fun end() {
        GameRepositoryImpl.createAllPots()
        GameRepositoryImpl.distributeStackToWinners()
        listener.onEndTurn()
    }

    fun onEndPartTurn() {
        if (GameRepositoryImpl.isTurnOver()) {
            end()
        } else {
            startPartTurn(GameRepositoryImpl.getNextPartTurn())
        }
    }
}
*/
