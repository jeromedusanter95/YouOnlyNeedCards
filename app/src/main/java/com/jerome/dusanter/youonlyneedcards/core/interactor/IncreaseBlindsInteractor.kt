package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class IncreaseBlindsInteractor() {

    fun execute() {
        GameRepositoryImpl.shouldIncreaseBlindNextTurn = true
    }
}
