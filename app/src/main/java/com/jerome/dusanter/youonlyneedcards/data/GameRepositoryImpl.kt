package com.jerome.dusanter.youonlyneedcards.data

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.StateBlind
import com.jerome.dusanter.youonlyneedcards.core.StatePlayer
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.utils.MutableCircularList

object GameRepositoryImpl {

    var listPlayers: MutableCircularList<Player> = MutableCircularList(mutableListOf())
    lateinit var settings: Settings
    lateinit var currentPlayer: Player

    fun addPlayer(idPlayer: String, name: String): Player {
        val player = Player(
            id = idPlayer,
            name = name,
            stack = settings.stack,
            stackBetTurn = 0,
            stackBetPartTurn = 0,
            statePlayer = StatePlayer.Playing,
            stateBlind = StateBlind.Nothing,
            actionPlayer = ActionPlayer.Nothing
        )
        listPlayers.add(player)
        return player
    }

    fun initializeStateBlind() {
        if (getNumberPlayersNotEliminated() == 2) {
            initializeStateBlindTwoPlayers()
        } else {
            initializeStateBlindThreePlayersOrMore()
        }
        resetStateBlindForEliminatedPlayers()
        withDrawBlindsToPlayer()
    }

    private fun getNumberPlayersNotEliminated(): Int {
        return listPlayers.filter { it.statePlayer != StatePlayer.Eliminate }.size
    }

    private fun initializeStateBlindTwoPlayers() {
        val previousDealerIndex = getDealerIndex()
        listPlayers[previousDealerIndex].stateBlind = StateBlind.BigBlind
        val currentDealerIndex = getIndexNextPlayerNotEliminated(previousDealerIndex + 1)
        listPlayers[currentDealerIndex].stateBlind = StateBlind.Dealer
    }

    private fun initializeStateBlindThreePlayersOrMore() {
        val previousDealerIndex = getDealerIndex()
        listPlayers[previousDealerIndex].stateBlind = StateBlind.Nothing
        val currentDealerIndex = getIndexNextPlayerNotEliminated(previousDealerIndex + 1)
        listPlayers[currentDealerIndex].stateBlind = StateBlind.Dealer
        val currentSmallBlindIndex = getIndexNextPlayerNotEliminated(currentDealerIndex + 1)
        listPlayers[currentSmallBlindIndex].stateBlind = StateBlind.SmallBlind
        val currentBigBlindIndex = getIndexNextPlayerNotEliminated(currentSmallBlindIndex + 1)
        listPlayers[currentBigBlindIndex].stateBlind = StateBlind.BigBlind
    }

    private fun getDealerIndex(): Int {
        return listPlayers.indexOf(listPlayers.find { it.stateBlind == StateBlind.Dealer })
    }

    private fun getIndexNextPlayerNotEliminated(startIndex: Int): Int {
        for (i in 0..listPlayers.size) {
            if (listPlayers[startIndex + i].statePlayer != StatePlayer.Eliminate) {
                return startIndex + i
            }
        }
        return startIndex
    }

    private fun resetStateBlindForEliminatedPlayers() {
        listPlayers.forEach {
            if (it.statePlayer == StatePlayer.Eliminate) {
                it.stateBlind = StateBlind.Nothing
            }
        }
    }

    private fun withDrawBlindsToPlayer() {
        val dealerIndex = getDealerIndex()
        val smallBlindIndex = getSmallBlindIndex()
        val bigBlindIndex = getBigBlindIndex()
        if (getNumberPlayersNotEliminated() == 2) {
            withDrawMoneyToPlayer(dealerIndex, settings.smallBlind)
            withDrawMoneyToPlayer(bigBlindIndex, settings.smallBlind * 2)
        } else {
            withDrawMoneyToPlayer(smallBlindIndex, settings.smallBlind)
            withDrawMoneyToPlayer(bigBlindIndex, settings.smallBlind * 2)
        }
    }

    private fun getSmallBlindIndex(): Int {
        return listPlayers.indexOf(listPlayers.find { it.stateBlind == StateBlind.SmallBlind })
    }

    private fun getBigBlindIndex(): Int {
        return listPlayers.indexOf(listPlayers.find { it.stateBlind == StateBlind.BigBlind })
    }

    private fun withDrawMoneyToPlayer(index: Int, stack: Int) {
        listPlayers[index].stack -= stack
        listPlayers[index].stackBetTurn += stack
        listPlayers[index].stackBetPartTurn += stack
    }

    fun check() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Check)
        updatePlayer(currentPlayer)
    }

    fun call() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Call)
        updatePlayer(currentPlayer)
    }

    fun raise(stackRaised: Int) {
        currentPlayer = currentPlayer.copy(
            actionPlayer = ActionPlayer.Raise,
            stack = currentPlayer.stack - stackRaised,
            stackBetTurn = currentPlayer.stackBetTurn + stackRaised,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + stackRaised
        )
        updatePlayer(currentPlayer)
    }

    fun fold() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Fold)
        updatePlayer(currentPlayer)
    }

    fun allin() {
        currentPlayer = currentPlayer.copy(
            actionPlayer = ActionPlayer.AllIn,
            stackBetTurn = currentPlayer.stackBetTurn + currentPlayer.stack,
            stackBetPartTurn = currentPlayer.stackBetPartTurn + currentPlayer.stack,
            stack = 0
        )
        updatePlayer(currentPlayer)
    }

    fun getPossibleActions(): List<ActionPlayer> {
        //TODO implement this function later
        val list = mutableListOf<ActionPlayer>()
        list.add(ActionPlayer.Check)
        list.add(ActionPlayer.Raise)
        list.add(ActionPlayer.Fold)
        return list
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

    fun resetActionPlayerExceptFoldedAndAllIn() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate
                && it.actionPlayer != ActionPlayer.Fold
                && it.actionPlayer != ActionPlayer.AllIn
            ) {
                it.actionPlayer = ActionPlayer.Nothing
            }
        }
    }

    fun resetStackBetTurn() {
        listPlayers.forEach { it.stackBetTurn = 0 }
    }

    fun resetStackBetPartTurn() {
        listPlayers.forEach { it.stackBetPartTurn = 0 }
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

    fun initializeCurrentPlayerAfterBigBlind() {
        val bigBlindIndex = getBigBlindIndex()
        listPlayers[getIndexNextPlayerNotEliminated(bigBlindIndex + 1)].statePlayer = StatePlayer.CurrentTurn
    }
}
