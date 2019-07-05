package com.jerome.dusanter.youonlyneedcards.data

import com.jerome.dusanter.youonlyneedcards.core.*
import com.jerome.dusanter.youonlyneedcards.utils.MutableCircularList

object GameRepositoryImpl {

    var listPlayers: MutableCircularList<Player> = MutableCircularList(mutableListOf())
    lateinit var settings: Settings
    lateinit var currentPlayer: Player
    var currentStackTurn = 0
    private var currentMaxRaisePartTurn = 0
    var currentStateTurn = StateTurn.PreFlop

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
        val currentDealerIndex = getIndexNextPlayerNotEliminatedOrFolded(previousDealerIndex + 1)
        listPlayers[currentDealerIndex].stateBlind = StateBlind.Dealer
    }

    private fun initializeStateBlindThreePlayersOrMore() {
        val previousDealerIndex = getDealerIndex()
        listPlayers[previousDealerIndex].stateBlind = StateBlind.Nothing
        val currentDealerIndex = getIndexNextPlayerNotEliminatedOrFolded(previousDealerIndex + 1)
        listPlayers[currentDealerIndex].stateBlind = StateBlind.Dealer
        val currentSmallBlindIndex = getIndexNextPlayerNotEliminatedOrFolded(currentDealerIndex + 1)
        listPlayers[currentSmallBlindIndex].stateBlind = StateBlind.SmallBlind
        val currentBigBlindIndex = getIndexNextPlayerNotEliminatedOrFolded(currentSmallBlindIndex + 1)
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

    private fun getIndexNextPlayerNotEliminatedOrFolded(startIndex: Int): Int {
        for (i in 0..listPlayers.size) {
            if (listPlayers[startIndex + i].statePlayer != StatePlayer.Eliminate
                && listPlayers[startIndex + i].actionPlayer != ActionPlayer.Fold
            ) {
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
            if (listPlayers[dealerIndex].stack < settings.smallBlind) {
                withDrawMoneyToPlayer(dealerIndex, listPlayers[dealerIndex].stack)
                listPlayers[dealerIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(dealerIndex, settings.smallBlind)
            }
            if (listPlayers[bigBlindIndex].stack < settings.smallBlind * 2) {
                withDrawMoneyToPlayer(bigBlindIndex, listPlayers[bigBlindIndex].stack)
                listPlayers[bigBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(bigBlindIndex, settings.smallBlind * 2)
            }
        } else {
            if (listPlayers[smallBlindIndex].stack < settings.smallBlind) {
                withDrawMoneyToPlayer(smallBlindIndex, listPlayers[smallBlindIndex].stack)
                listPlayers[smallBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(smallBlindIndex, settings.smallBlind)
            }
            if (listPlayers[bigBlindIndex].stack < settings.smallBlind * 2) {
                withDrawMoneyToPlayer(bigBlindIndex, listPlayers[bigBlindIndex].stack)
                listPlayers[bigBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(bigBlindIndex, settings.smallBlind * 2)
            }
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
        currentStackTurn += stack
        setCurrentMaxRaisePartTurn()
    }

    private fun setCurrentMaxRaisePartTurn() {
        currentMaxRaisePartTurn = listPlayers.maxBy {
            it.stackBetPartTurn
        }?.stackBetPartTurn!!
    }

    fun initializeCurrentPlayerAfterBigBlind() {
        val bigBlindIndex = getBigBlindIndex()
        currentPlayer = listPlayers[getIndexNextPlayerNotEliminatedOrFolded(bigBlindIndex + 1)]
        currentPlayer.statePlayer = StatePlayer.CurrentTurn
    }

    fun check() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        listPlayers[currentPlayerIndex].actionPlayer = ActionPlayer.Check
    }

    fun call() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(
            currentPlayerIndex,
            currentMaxRaisePartTurn - currentPlayer.stackBetPartTurn
        )
        listPlayers[currentPlayerIndex].actionPlayer = ActionPlayer.Call
    }

    fun raise(stackRaised: Int) {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(currentPlayerIndex, stackRaised)
        listPlayers[currentPlayerIndex].actionPlayer = ActionPlayer.Raise
    }

    fun fold() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        listPlayers[currentPlayerIndex].actionPlayer = ActionPlayer.Fold
    }

    fun allin() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(currentPlayerIndex, currentPlayer.stack)
        listPlayers[currentPlayerIndex].actionPlayer = ActionPlayer.AllIn
    }

    private fun getCurrentPlayerIndex(): Int {
        return listPlayers.indexOf(listPlayers.find { it.statePlayer == StatePlayer.CurrentTurn })
    }

    fun getPossibleActions(): List<ActionPlayer> {
        val list = mutableListOf<ActionPlayer>()
        when {
            currentMaxRaisePartTurn == settings.smallBlind * 2
                    && currentPlayer.stackBetPartTurn == currentMaxRaisePartTurn
                    && currentStateTurn == StateTurn.PreFlop
                    && !didAllPlayersPlayed()
            -> {
                list.add(ActionPlayer.Check)
                list.add(ActionPlayer.Raise)
                list.add(ActionPlayer.Fold)
            }
            currentMaxRaisePartTurn == 0 -> {
                list.add(ActionPlayer.Check)
                list.add(ActionPlayer.Raise)
                list.add(ActionPlayer.Fold)
            }
            currentMaxRaisePartTurn >= currentPlayer.stackBetPartTurn + currentPlayer.stack -> {
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
        listPlayers[previousCurrentPlayerIndex].statePlayer = StatePlayer.Playing
        listPlayers[getIndexNextPlayerNotEliminatedOrFolded(previousCurrentPlayerIndex + 1)].statePlayer =
            StatePlayer.CurrentTurn
        currentPlayer = listPlayers[getIndexNextPlayerNotEliminatedOrFolded(
            previousCurrentPlayerIndex + 1
        )]
    }

    fun moveToFirstPlayerAvailableFromSmallBlind() {
        val previousCurrentPlayerIndex = getCurrentPlayerIndex()
        listPlayers[previousCurrentPlayerIndex].statePlayer = StatePlayer.Playing
        val newCurrentPlayerIndex = getIndexNextPlayerNotEliminatedOrFolded(getSmallBlindIndex())
        listPlayers[newCurrentPlayerIndex].statePlayer = StatePlayer.CurrentTurn
        currentPlayer = listPlayers[newCurrentPlayerIndex]
    }


    fun isPartTurnOver(): Boolean {
        return (didAllPlayersCheck() || didAllPlayersPaidMaxRaiseValue()) && didAllPlayersPlayed()
    }

    private fun didAllPlayersPlayed(): Boolean {
        return listPlayers.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold
        }
            .find { it.actionPlayer == ActionPlayer.Nothing } == null
    }

    private fun didAllPlayersCheck(): Boolean {
        return listPlayers.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold
        }
            .find { it.actionPlayer != ActionPlayer.Check } == null
    }

    private fun didAllPlayersPaidMaxRaiseValue(): Boolean {
        return listPlayers.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold
        }
            .find { it.stackBetPartTurn != currentMaxRaisePartTurn } == null
    }

    fun isTurnOver(): Boolean {
        return (currentStateTurn == StateTurn.River && isPartTurnOver()) || isThereOnlyOnePlayerLeftInPartTurn()
    }

    private fun isThereOnlyOnePlayerLeftInPartTurn(): Boolean {
        return listPlayers.filter { it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold }.size == 1
    }

    fun endPartTurn() {
        currentMaxRaisePartTurn = 0
        setCurrentStateTurn()
        resetActionPlayerExceptFoldedAndAllIn()
        resetStackBetPartTurn()
    }

    private fun setCurrentStateTurn() {
        currentStateTurn = when (currentStateTurn) {
            StateTurn.PreFlop -> StateTurn.Flop
            StateTurn.Flop -> StateTurn.Turn
            StateTurn.Turn -> StateTurn.River
            else -> StateTurn.PreFlop
        }
    }

    fun endTurn() {
        currentMaxRaisePartTurn = 0
        currentStateTurn = StateTurn.PreFlop
        currentStackTurn = 0
        resetActionPlayer()
        resetStackBetPartTurn()
        resetCurrentPlayerStatePlayer()
    }

    private fun resetCurrentPlayerStatePlayer() {
        listPlayers[getCurrentPlayerIndex()].statePlayer = StatePlayer.Playing
    }

    private fun resetActionPlayerExceptFoldedAndAllIn() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate
                && it.actionPlayer != ActionPlayer.Fold
                && it.actionPlayer != ActionPlayer.AllIn
            ) {
                it.actionPlayer = ActionPlayer.Nothing
            }
        }
    }

    private fun resetActionPlayer() {
        listPlayers.forEach {
            if (it.statePlayer != StatePlayer.Eliminate) {
                it.actionPlayer = ActionPlayer.Nothing
            }
        }
    }

    private fun resetStackBetPartTurn() {
        listPlayers.forEach { it.stackBetPartTurn = 0 }
    }

    private fun resetStackBetTurn() {
        listPlayers.forEach { it.stackBetTurn = 0 }
    }

    fun isIncreaseBlindsEnabled(): Boolean {
        return settings.isIncreaseBlindsEnabled
    }

    fun createAllPot(): MutableList<Pot> {
        val potList = mutableListOf<Pot>()
        var potentialWinners: MutableList<Player> = mutableListOf()
        listPlayers.filter { it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold }.forEach {
            potentialWinners.add(Player(it.id, it.name, it.stack, it.stackBetTurn))
        }
        while (!isAllPotCreated(potentialWinners)) {
            potentialWinners = potentialWinners.filter { it.stackBetTurn != 0 }.toMutableList()
            val minStackBetTurn = potentialWinners.minBy {
                it.stackBetTurn
            }?.stackBetTurn
            var stackPot = 0
            potentialWinners.forEach {
                it.stackBetTurn -= minStackBetTurn!!
                stackPot += minStackBetTurn
            }
            potList.add(Pot(potentialWinners, stackPot))
        }
        val playerWHoHasToRecoverMoney = potentialWinners.find { it.stackBetTurn != 0 }
        if (playerWHoHasToRecoverMoney != null) {
            addStackToPlayer(getPlayerIndexById(playerWHoHasToRecoverMoney.id), playerWHoHasToRecoverMoney.stackBetTurn)
        }
        if (potentialWinners.size == 1) {
            potList.add(Pot(potentialWinners, currentStackTurn))
        }
        return potList
    }

    private fun isAllPotCreated(potentialWinners: List<Player>): Boolean {
        return potentialWinners.filter { it.stackBetTurn != 0 }.size <= 1
    }

    fun distributePotsToWinners(winnerList: List<Winner>): MutableList<PlayerEndTurn> {
        val playerEndTurnList = mutableListOf<PlayerEndTurn>()
        winnerList.forEach {
            playerEndTurnList.add(PlayerEndTurn(it.id, getPlayerNameById(it.id), it.stackWon, true))
            addStackToPlayer(getPlayerIndexById(it.id), it.stackWon)
        }
        listPlayers.forEach { player ->
            if (winnerList.find { it.id == player.id } == null) {
                playerEndTurnList.add(PlayerEndTurn(player.id, player.name, player.stackBetTurn, false))
            }
        }
        resetStackBetTurn()
        return playerEndTurnList
    }

    private fun getPlayerNameById(id: String): String {
        return listPlayers.find { it.id == id }!!.name
    }

    private fun getPlayerIndexById(id: String): Int {
        val player = listPlayers.find { it.id == id }
        return listPlayers.indexOf(player)
    }

    private fun addStackToPlayer(index: Int, stack: Int) {
        listPlayers[index].stack += stack
    }

    fun startTimerIncreaseBlinds() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
