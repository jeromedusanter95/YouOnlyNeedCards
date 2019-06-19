package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class Game : Turn.Listener {
    private lateinit var repository: GameRepositoryImpl

    fun start(settings: Settings) {
        repository.initializeSettings(settings)
        repository.distributeStackToPlayers()
        if (repository.isIncreaseBlindsEnabled()) {
            repository.startTimerIncreaseBlinds()
        }
        startTurn()
    }

    private fun startTurn() {
        val turn = Turn(repository, this)
        turn.start()
    }

    private fun end() {
        if (repository.isMoneyBetEnabled()) {
            repository.distributeMoneyToWinners()
        }
    }

    fun save() {
        repository.save()
    }

    fun recave() {
        repository.recave()
    }

    override fun onEndTurn() {
        if (repository.isGameOver()) {
            end()
        } else {
            if (repository.shouldIncreaseBlinds()
            ) {
                repository.increaseBlinds()
                repository.startTimerIncreaseBlinds()
            }
            startTurn()
        }
    }
}