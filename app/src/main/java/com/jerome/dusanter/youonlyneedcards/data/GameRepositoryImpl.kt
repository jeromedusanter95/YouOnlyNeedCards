package com.jerome.dusanter.youonlyneedcards.data

import com.jerome.dusanter.youonlyneedcards.core.*

object GameRepositoryImpl {

    var listPlayers: MutableList<Player> = mutableListOf()
    lateinit var settings: Settings
    lateinit var currentPlayer: Player

    fun addPlayer(idPlayer: String, name: String): Player {
        val player = Player(
            id = idPlayer,
            name = name,
            stack = settings.stack,
            stackBetTurn = 0,
            stackBetPartTurn = 0,
            statePlayer = StatePlayer.Nothing,
            stateBlind = StateBlind.Nothing
        )
        listPlayers.add(player)
        return player
    }

    fun initializeStateBlind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun check() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Check)
        updatePlayer(currentPlayer)
    }

    fun call() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Call)
        updatePlayer(currentPlayer)
    }

    fun raise(stackRaised: Int) {
        currentPlayer = currentPlayer.copy(
            statePlayer = StatePlayer.Raise,
            stack = currentPlayer.stack - stackRaised,
            stackBetTurn = currentPlayer.stackBetTurn + stackRaised,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + stackRaised
        )
        updatePlayer(currentPlayer)
    }

    fun fold() {
        currentPlayer = currentPlayer.copy(statePlayer = StatePlayer.Fold)
        updatePlayer(currentPlayer)
    }

    fun allin() {
        currentPlayer = currentPlayer.copy(
            statePlayer = StatePlayer.AllIn,
            stackBetTurn = currentPlayer.stackBetTurn + currentPlayer.stack,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + currentPlayer.stack,
            stack = 0
        )
        updatePlayer(currentPlayer)
    }

    fun getPossibleActions(): List<ActionPlayer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun moveToNextPlayerAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isPartTurnOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun moveToFirstPlayerAfterBigBlind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun moveToFirstPlayerAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun resetStatePlayer() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate) {
                it.statePlayer = StatePlayer.Nothing
            }
        }
    }

    fun createAllPots() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun distributeStackToWinners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getNextPartTurn(): StateTurn {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isTurnOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun resetStatePlayerExceptFoldedAndAllIn() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate
                && it.statePlayer != StatePlayer.Fold
                && it.statePlayer != StatePlayer.AllIn
            ) {
                it.statePlayer = StatePlayer.Nothing
            }
        }
    }

    fun resetStackBetTurn() {
        listPlayers.forEach {
            it.stackBetTurn = 0
        }
    }

    fun resetStackBetPartTurn() {
        listPlayers.forEach {
            it.stackBetPartTurn = 0
        }
    }

    fun distributeMoneyToWinners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun shouldIncreaseBlinds(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun increaseBlinds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun startTimerIncreaseBlinds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isIncreaseBlindsEnabled(): Boolean {
        return settings.isIncreaseBlindsEnabled
    }

    fun isMoneyBetEnabled(): Boolean {
        return settings.isMoneyBetEnabled
    }

    fun isGameOver(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun recave() {
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
