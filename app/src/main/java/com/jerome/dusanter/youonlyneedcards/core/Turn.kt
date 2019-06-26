package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class Turn(private val listener: Listener) : PartTurn.Listener {

    fun start() {
        GameRepositoryImpl.initializeStateBlind()
        GameRepositoryImpl.resetStatePlayer()
        GameRepositoryImpl.resetStackBetTurn()
        startPartTurn(StateTurn.PreFlop)
    }

    private fun startPartTurn(stateTurn: StateTurn) {
        val partTurn = PartTurn(stateTurn,this)
        partTurn.start()
    }

    private fun end() {
        GameRepositoryImpl.createAllPots()
        GameRepositoryImpl.distributeStackToWinners()
        listener.onEndTurn()
    }

    override fun onEndPartTurn() {
        if (GameRepositoryImpl.isTurnOver()) {
            end()
        } else {
            startPartTurn(GameRepositoryImpl.getNextPartTurn())
        }
    }

    interface Listener {
        fun onEndTurn()
    }
}