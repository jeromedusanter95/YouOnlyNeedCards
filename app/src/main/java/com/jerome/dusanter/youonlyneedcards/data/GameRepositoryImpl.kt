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
    var stackTurn = 0
    var maxRaisePartTurn = 0
    var stateTurn = StateTurn.PreFlop

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

    fun initializeListWithGoodOrder() {
        listPlayers.sortWith(Comparator { player1, player2 ->
            player1.id.toInt().compareTo(player2.id.toInt())
        })
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
        val dealerPlayer = listPlayers.find { it.stateBlind == StateBlind.Dealer }
        return if (dealerPlayer != null) {
            listPlayers.indexOf(dealerPlayer)
        } else {
            0
        }
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
        stackTurn += stack
        if (maxRaisePartTurn < stack) {
            maxRaisePartTurn = stack
        }
    }

    fun initializeCurrentPlayerAfterBigBlind() {
        val bigBlindIndex = getBigBlindIndex()
        currentPlayer = listPlayers[getIndexNextPlayerNotEliminated(bigBlindIndex + 1)]
        currentPlayer.statePlayer = StatePlayer.CurrentTurn
    }

    fun check() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Check)
        updatePlayer(currentPlayer)
    }

    fun call() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Call)
        withDrawMoneyToPlayer(getCurrentPlayerIndex(), maxRaisePartTurn)
        updatePlayer(currentPlayer)
    }

    fun raise(stackRaised: Int) {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Raise)
        withDrawMoneyToPlayer(getCurrentPlayerIndex(), stackRaised)
        updatePlayer(currentPlayer)
    }

    fun fold() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.Fold)
        updatePlayer(currentPlayer)
    }

    fun allin() {
        currentPlayer = currentPlayer.copy(actionPlayer = ActionPlayer.AllIn)
        withDrawMoneyToPlayer(getCurrentPlayerIndex(), currentPlayer.stack)
        updatePlayer(currentPlayer)
    }

    private fun getCurrentPlayerIndex(): Int {
        return listPlayers.indexOf(listPlayers.find { it.statePlayer == StatePlayer.CurrentTurn })
    }

    fun getPossibleActions(): List<ActionPlayer> {
        val list = mutableListOf<ActionPlayer>()
        when {
            maxRaisePartTurn == 0 -> {
                list.add(ActionPlayer.Check)
                list.add(ActionPlayer.Raise)
                list.add(ActionPlayer.Fold)
            }
            maxRaisePartTurn >= currentPlayer.stackBetPartTurn + currentPlayer.stack -> {
                list.add(ActionPlayer.AllIn)
                list.add(ActionPlayer.Fold)
            }
            else -> {
                list.add(ActionPlayer.Call)
                list.add(ActionPlayer.Raise)
                list.add(ActionPlayer.Fold)
            }
        }
        return list
    }

    fun moveToNextPlayerAvailable() {
        val previousCurrentPlayerIndex = getCurrentPlayerIndex()
        listPlayers[getIndexNextPlayerNotEliminated(previousCurrentPlayerIndex)].statePlayer = StatePlayer.Playing
        listPlayers[getIndexNextPlayerNotEliminated(previousCurrentPlayerIndex + 1)].statePlayer = StatePlayer.CurrentTurn
    }

    fun isPartTurnOver(): Boolean {
        return false
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
        return false
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
}
