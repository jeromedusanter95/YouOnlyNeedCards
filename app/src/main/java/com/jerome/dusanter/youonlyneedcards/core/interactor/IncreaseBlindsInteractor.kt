package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Game
import javax.inject.Inject

class IncreaseBlindsInteractor @Inject internal constructor() {
    fun execute() {
        Game.shouldIncreaseBlindNextTurn = true
    }
}
