package com.jerome.dusanter.youonlyneedcards.data

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.StatePlayer
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.core.boundary.GameRepository

object GameRepositoryImpl : GameRepository {

    /** OVERRIDE METHODS **/
    private lateinit var listPlayers: MutableList<Player>
    lateinit var settings: Settings
    private lateinit var currentPlayer: Player

    override fun check() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Check)
        updatePlayer(currentPlayer)
    }

    override fun call() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Call)
        updatePlayer(currentPlayer)
    }

    override fun raise(stackRaised: Int) {
        currentPlayer = currentPlayer.copy(
            statePlayer = StatePlayer.Raise,
            stack = currentPlayer.stack - stackRaised,
            stackBetTurn = currentPlayer.stackBetTurn + stackRaised,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + stackRaised
        )
        updatePlayer(currentPlayer)
    }

    override fun fold() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Fold)
        updatePlayer(currentPlayer)
    }

    override fun allin() {
        currentPlayer = currentPlayer.copy(
            statePlayer = StatePlayer.AllIn,
            stackBetTurn = currentPlayer.stackBetTurn + currentPlayer.stack,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + currentPlayer.stack,
            stack = 0
        )
        updatePlayer(currentPlayer)
    }

    override fun getPossibleActions(): List<ActionPlayer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveToNextPlayerAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isPartTurnOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveToFirstPlayerAfterBigBlind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveToFirstPlayerAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initializeStateBlind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStatePlayer() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate) {
                it.statePlayer = StatePlayer.Nothing
            }
        }
    }

    override fun createAllPots() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun distributeStackToWinners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNextPartTurn(): StateTurn {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isTurnOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStatePlayerExceptFoldedAndAllIn() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate
                && it.statePlayer != StatePlayer.Fold
                && it.statePlayer != StatePlayer.AllIn
            ) {
                it.statePlayer = StatePlayer.Nothing
            }
        }
    }

    override fun resetStackBetTurn() {
        listPlayers.forEach {
            it.stackBetTurn = 0
        }
    }

    override fun resetStackBetPartTurn() {
        listPlayers.forEach {
            it.stackBetPartTurn = 0
        }
    }

    override fun distributeMoneyToWinners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shouldIncreaseBlinds(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun increaseBlinds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startTimerIncreaseBlinds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isIncreaseBlindsEnabled(): Boolean {
        return settings.isIncreaseBlindsEnabled
    }

    override fun isMoneyBetEnabled(): Boolean {
        return settings.isMoneyBetEnabled
    }

    override fun distributeStackToPlayers() {
        listPlayers.forEach {
            it.stack = settings.stack
        }
    }

    override fun initializeAllPlayers(listPlayers: MutableList<Player>) {
    }

    override fun initializeSettings(settings: Settings) {
    }

    override fun isGameOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun recave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /** PRIVATE METHODS **/

    private fun updatePlayer(player: Player) {
        listPlayers.replace(player) { it.id == player.id }
    }

    //TODO verify if it's working
    private fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> {
        return map {
            if (block(it)) newValue else it
        }
    }
}
