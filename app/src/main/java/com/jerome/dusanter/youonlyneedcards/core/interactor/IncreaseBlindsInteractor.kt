package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game

class IncreaseBlindsInteractor() {

    fun execute() {
        Game.shouldIncreaseBlindNextTurn = true
    }
}
