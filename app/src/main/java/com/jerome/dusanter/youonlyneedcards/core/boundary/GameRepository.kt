package com.jerome.dusanter.youonlyneedcards.core.boundary

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.StateTurn

interface GameRepository {
    fun check()
    fun call()
    fun raise(stackRaised: Int)
    fun fold()
    fun allin()
    fun getPossibleActions(): List<ActionPlayer>
    fun moveToNextPlayerAvailable()
    fun isPartTurnOver(): Boolean
    fun moveToFirstPlayerAfterBigBlind()
    fun moveToFirstPlayerAvailable()
    fun initializeStateBlind()
    fun resetStatePlayer()
    fun resetStatePlayerExceptFoldedAndAllIn()
    fun createAllPots()
    fun distributeStackToWinners()
    fun getNextPartTurn(): StateTurn
    fun isTurnOver(): Boolean
    fun resetStackBetTurn()
    fun resetStackBetPartTurn()
    fun distributeMoneyToWinners()
    fun shouldIncreaseBlinds(): Boolean
    fun increaseBlinds()
    fun startTimerIncreaseBlinds()
    fun isIncreaseBlindsEnabled(): Boolean
    fun isMoneyBetEnabled(): Boolean
    fun distributeStackToPlayers()
    fun initializeAllPlayers(listPlayers: MutableList<Player>)
    fun isGameOver(): Boolean
    fun save()
    fun recave()
    fun initializeSettings(settings: Settings)
}