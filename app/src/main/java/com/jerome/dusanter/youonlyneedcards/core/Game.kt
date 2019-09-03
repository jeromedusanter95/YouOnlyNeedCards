package com.jerome.dusanter.youonlyneedcards.core

import com.jerome.dusanter.youonlyneedcards.utils.MutableCircularList

object Game {

    var playersList: MutableCircularList<Player> = MutableCircularList(mutableListOf())
    lateinit var settings: Settings
    lateinit var currentPlayer: Player
    var currentStackTurn = 0
    private var currentMaxRaisePartTurn = 0
    var currentStateTurn = StateTurn.PreFlop
    var shouldIncreaseBlindNextTurn = false
    var shouldStartTimer = false
    var timeRemainingBeforeIncreaseBlinds: Long = 0

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
        playersList.add(player)
        return player
    }

    fun initializeListWithGoodOrder() {
        playersList.sortWith(Comparator { player1, player2 ->
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

    fun getNumberPlayersNotEliminated(): Int {
        return playersList.filter { it.statePlayer != StatePlayer.Eliminate }.size
    }

    private fun initializeStateBlindTwoPlayers() {
        val previousDealerIndex = getDealerIndex()
        playersList[previousDealerIndex].stateBlind = StateBlind.BigBlind
        val currentDealerIndex =
            getIndexNextPlayerNotEliminatedOrFolded(
                previousDealerIndex + 1
            )
        playersList[currentDealerIndex].stateBlind = StateBlind.Dealer
    }

    private fun initializeStateBlindThreePlayersOrMore() {
        val previousDealerIndex = getDealerIndex()
        playersList[previousDealerIndex].stateBlind = StateBlind.Nothing
        val currentDealerIndex =
            getIndexNextPlayerNotEliminatedOrFolded(
                previousDealerIndex + 1
            )
        playersList[currentDealerIndex].stateBlind = StateBlind.Dealer
        val currentSmallBlindIndex =
            getIndexNextPlayerNotEliminatedOrFolded(
                currentDealerIndex + 1
            )
        playersList[currentSmallBlindIndex].stateBlind = StateBlind.SmallBlind
        val currentBigBlindIndex =
            getIndexNextPlayerNotEliminatedOrFolded(
                currentSmallBlindIndex + 1
            )
        playersList[currentBigBlindIndex].stateBlind = StateBlind.BigBlind
    }

    private fun getDealerIndex(): Int {
        val dealerPlayer = playersList.find { it.stateBlind == StateBlind.Dealer }
        return if (dealerPlayer != null) {
            playersList.indexOf(dealerPlayer)
        } else {
            0
        }
    }

    private fun getIndexNextPlayerNotEliminatedOrFolded(startIndex: Int): Int {
        for (i in 0..playersList.size) {
            if (playersList[startIndex + i].statePlayer != StatePlayer.Eliminate
                && playersList[startIndex + i].actionPlayer != ActionPlayer.Fold
            ) {
                return startIndex + i
            }
        }
        return startIndex
    }

    private fun getIndexNextPlayerNotEliminatedOrFoldedOrAllin(startIndex: Int): Int {
        for (i in 0..playersList.size) {
            if (playersList[startIndex + i].statePlayer != StatePlayer.Eliminate
                && playersList[startIndex + i].actionPlayer != ActionPlayer.Fold
                && playersList[startIndex + i].actionPlayer != ActionPlayer.AllIn
            ) {
                return startIndex + i
            }
        }
        return startIndex
    }

    private fun resetStateBlindForEliminatedPlayers() {
        playersList.forEach {
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
            if (playersList[dealerIndex].stack < settings.smallBlind) {
                withDrawMoneyToPlayer(
                    dealerIndex,
                    playersList[dealerIndex].stack
                )
                playersList[dealerIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(
                    dealerIndex,
                    settings.smallBlind
                )
            }
            if (playersList[bigBlindIndex].stack < settings.smallBlind * 2) {
                withDrawMoneyToPlayer(
                    bigBlindIndex,
                    playersList[bigBlindIndex].stack
                )
                playersList[bigBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(
                    bigBlindIndex,
                    settings.smallBlind * 2
                )
            }
        } else {
            if (playersList[smallBlindIndex].stack < settings.smallBlind) {
                withDrawMoneyToPlayer(
                    smallBlindIndex,
                    playersList[smallBlindIndex].stack
                )
                playersList[smallBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(
                    smallBlindIndex,
                    settings.smallBlind
                )
            }
            if (playersList[bigBlindIndex].stack < settings.smallBlind * 2) {
                withDrawMoneyToPlayer(
                    bigBlindIndex,
                    playersList[bigBlindIndex].stack
                )
                playersList[bigBlindIndex].actionPlayer = ActionPlayer.AllIn
            } else {
                withDrawMoneyToPlayer(
                    bigBlindIndex,
                    settings.smallBlind * 2
                )
            }
        }
    }

    private fun getSmallBlindIndex(): Int {
        return playersList.indexOf(playersList.find { it.stateBlind == StateBlind.SmallBlind })
    }

    private fun getBigBlindIndex(): Int {
        return playersList.indexOf(playersList.find { it.stateBlind == StateBlind.BigBlind })
    }

    private fun withDrawMoneyToPlayer(index: Int, stack: Int) {
        playersList[index].stack -= stack
        playersList[index].stackBetTurn += stack
        playersList[index].stackBetPartTurn += stack
        currentStackTurn += stack
        setCurrentMaxRaisePartTurn()
    }

    private fun setCurrentMaxRaisePartTurn() {
        currentMaxRaisePartTurn = playersList.maxBy {
            it.stackBetPartTurn
        }?.stackBetPartTurn!!
    }

    fun initializeCurrentPlayerAfterBigBlind() {
        val bigBlindIndex = getBigBlindIndex()
        currentPlayer = playersList[getIndexNextPlayerNotEliminatedOrFolded(
            bigBlindIndex + 1
        )]
        currentPlayer.statePlayer = StatePlayer.CurrentTurn
    }

    fun check() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        playersList[currentPlayerIndex].actionPlayer = ActionPlayer.Check
    }

    fun call() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(
            currentPlayerIndex,
            currentMaxRaisePartTurn - currentPlayer.stackBetPartTurn
        )
        playersList[currentPlayerIndex].actionPlayer = ActionPlayer.Call
    }

    fun raise(stackRaised: Int) {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(
            currentPlayerIndex,
            stackRaised
        )
        playersList[currentPlayerIndex].actionPlayer = ActionPlayer.Raise
    }

    fun fold() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        playersList[currentPlayerIndex].actionPlayer = ActionPlayer.Fold
    }

    fun allin() {
        val currentPlayerIndex = getCurrentPlayerIndex()
        withDrawMoneyToPlayer(
            currentPlayerIndex,
            currentPlayer.stack
        )
        playersList[currentPlayerIndex].actionPlayer = ActionPlayer.AllIn
    }

    private fun getCurrentPlayerIndex(): Int {
        return playersList.indexOf(playersList.find { it.statePlayer == StatePlayer.CurrentTurn })
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
        val previousCurrentPlayerIndex =
            getCurrentPlayerIndex()
        playersList[previousCurrentPlayerIndex].statePlayer = StatePlayer.Playing
        playersList[getIndexNextPlayerNotEliminatedOrFoldedOrAllin(
            previousCurrentPlayerIndex + 1
        )].statePlayer =
            StatePlayer.CurrentTurn
        currentPlayer = playersList[getIndexNextPlayerNotEliminatedOrFoldedOrAllin(
            previousCurrentPlayerIndex + 1
        )]
    }

    fun moveToFirstPlayerAvailableFromSmallBlind() {
        val previousCurrentPlayerIndex =
            getCurrentPlayerIndex()
        playersList[previousCurrentPlayerIndex].statePlayer = StatePlayer.Playing
        val newCurrentPlayerIndex =
            getIndexNextPlayerNotEliminatedOrFoldedOrAllin(
                getSmallBlindIndex()
            )
        playersList[newCurrentPlayerIndex].statePlayer = StatePlayer.CurrentTurn
        currentPlayer = playersList[newCurrentPlayerIndex]
    }


    fun moveToBigBlindWhenRemainingOnlyTwoPlayers() {
        val previousCurrentPlayerIndex =
            getCurrentPlayerIndex()
        playersList[previousCurrentPlayerIndex].statePlayer = StatePlayer.Playing
        playersList[getBigBlindIndex()].statePlayer = StatePlayer.CurrentTurn
        currentPlayer = playersList[getBigBlindIndex()]
    }


    fun isPartTurnOver(): Boolean {
        return (didAllPlayersCheck() || didAllPlayersPaidMaxRaiseValue() || didAllPlayersAllin()) && didAllPlayersPlayed()
    }

    private fun didAllPlayersAllin(): Boolean {
        return playersList.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold
        }
            .find { it.actionPlayer != ActionPlayer.AllIn } == null
    }

    private fun didAllPlayersPlayed(): Boolean {
        return playersList.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold && it.actionPlayer != ActionPlayer.AllIn
        }
            .find { it.actionPlayer == ActionPlayer.Nothing } == null
    }

    private fun didAllPlayersCheck(): Boolean {
        return playersList.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold && it.actionPlayer != ActionPlayer.AllIn
        }
            .find { it.actionPlayer != ActionPlayer.Check } == null
    }

    private fun didAllPlayersPaidMaxRaiseValue(): Boolean {
        return playersList.filter {
            it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold && it.actionPlayer != ActionPlayer.AllIn
        }
            .find { it.stackBetPartTurn != currentMaxRaisePartTurn } == null
    }

    fun isTurnOver(): Boolean {
        return (currentStateTurn == StateTurn.River && isPartTurnOver()) || isTurnOverBeforeRiver() || isThereOnlyOnePlayerLeftInPartTurn()
    }

    private fun isThereOnlyOnePlayerLeftInPartTurn(): Boolean {
        return playersList.filter { it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold }.size == 1
    }

    private fun isTurnOverBeforeRiver(): Boolean {
        val nextCurrentPlayerIndex = getCurrentPlayerIndex() + 1
        return playersList.filter {
            it.statePlayer != StatePlayer.Eliminate
                    && it.actionPlayer != ActionPlayer.Fold
                    && it.actionPlayer != ActionPlayer.AllIn
        }.size <= 1 && currentMaxRaisePartTurn == playersList[getIndexNextPlayerNotEliminatedOrFoldedOrAllin(
            nextCurrentPlayerIndex
        )].stackBetPartTurn
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
        playersList[getCurrentPlayerIndex()].statePlayer = StatePlayer.Playing
    }

    private fun resetActionPlayerExceptFoldedAndAllIn() {
        playersList.forEach {
            if (it.statePlayer != StatePlayer.Eliminate
                && it.actionPlayer != ActionPlayer.Fold
                && it.actionPlayer != ActionPlayer.AllIn
            ) {
                it.actionPlayer = ActionPlayer.Nothing
            }
        }
    }

    private fun resetActionPlayer() {
        playersList.forEach {
            if (it.statePlayer != StatePlayer.Eliminate) {
                it.actionPlayer = ActionPlayer.Nothing
            }
        }
    }

    private fun resetStackBetPartTurn() {
        playersList.forEach { it.stackBetPartTurn = 0 }
    }

    private fun resetStackBetTurn() {
        playersList.forEach { it.stackBetTurn = 0 }
    }

    fun isIncreaseBlindsEnabled(): Boolean {
        return settings.isIncreaseBlindsEnabled
    }

    fun createAllPot(): MutableList<Pot> {
        val potList = mutableListOf<Pot>()
        var potentialWinners: MutableList<Player> = mutableListOf()
        playersList.filter { it.statePlayer != StatePlayer.Eliminate && it.actionPlayer != ActionPlayer.Fold }
            .forEach {
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
            addStackToPlayer(
                getPlayerIndexById(
                    playerWHoHasToRecoverMoney.id
                ),
                playerWHoHasToRecoverMoney.stackBetTurn
            )
        }
        if (potentialWinners.size == 1) {
            potList.add(
                Pot(
                    potentialWinners,
                    currentStackTurn
                )
            )
        }
        return potList
    }

    private fun isAllPotCreated(potentialWinners: List<Player>): Boolean {
        return potentialWinners.filter { it.stackBetTurn != 0 }.size <= 1
    }

    fun distributePotsToWinners(winnerList: List<Winner>): MutableList<PlayerEndTurn> {
        val playerEndTurnList = mutableListOf<PlayerEndTurn>()
        winnerList.forEach {
            addStackToPlayer(
                getPlayerIndexById(
                    it.id
                ), it.stackWon
            )
        }
        playersList.filter { it.statePlayer != StatePlayer.Eliminate }.forEach { player ->
            when {
                winnerList.find { it.id == player.id && it.stackWon > player.stackBetTurn } != null -> {
                    val stackWon =
                        winnerList.find { it.id == player.id && it.stackWon > player.stackBetTurn }!!.stackWon
                    playerEndTurnList.add(
                        PlayerEndTurn(
                            player.id,
                            player.name,
                            stackWon - player.stackBetTurn,
                            true
                        )
                    )
                }
                winnerList.find { it.id == player.id && it.stackWon <= player.stackBetTurn } != null -> {
                    val stackWon =
                        winnerList.find { it.id == player.id && it.stackWon <= player.stackBetTurn }!!.stackWon
                    playerEndTurnList.add(
                        PlayerEndTurn(
                            player.id,
                            player.name,
                            player.stackBetTurn - stackWon,
                            false
                        )
                    )
                }
                else -> playerEndTurnList.add(
                    PlayerEndTurn(
                        player.id,
                        player.name,
                        player.stackBetTurn,
                        false
                    )
                )
            }
        }
        setStatePlayerEliminateForPLayerWhoLost()
        resetStackBetTurn()
        return playerEndTurnList
    }

    private fun setStatePlayerEliminateForPLayerWhoLost() {
        playersList.filter { it.statePlayer != StatePlayer.Eliminate && it.stack == 0 }.forEach {
            it.statePlayer = StatePlayer.Eliminate
        }
    }

    private fun getPlayerIndexById(id: String): Int {
        val player = playersList.find { it.id == id }
        return playersList.indexOf(player)
    }

    private fun addStackToPlayer(index: Int, stack: Int) {
        playersList[index].stack += stack
    }

    fun isGameOver(): Boolean {
        return playersList.filter { it.statePlayer != StatePlayer.Eliminate }.size < 2
    }

    fun getListPlayerEndGame(): List<PlayerEndGame> {
        return playersList.map {
            if (it.stack > settings.stack) {
                PlayerEndGame(it.id, it.name, it.stack, true)
            } else {
                PlayerEndGame(it.id, it.name, it.stack, false)
            }
        }
    }

    fun increaseBlinds() {
        settings.smallBlind *= 2
        shouldIncreaseBlindNextTurn = false
        shouldStartTimer = true
    }

    fun rebuyPlayer(playerId: String): Player {
        val playerWhoWantToRebuy = playersList.find { it.id == playerId }
        if (playerWhoWantToRebuy != null && playerWhoWantToRebuy.statePlayer == StatePlayer.Eliminate) {
            playerWhoWantToRebuy.statePlayer = StatePlayer.Playing
            playerWhoWantToRebuy.stack = settings.stack
        }
        return playerWhoWantToRebuy!!
    }

    fun resetGame() {
        playersList = MutableCircularList(mutableListOf())
        settings = Settings(
            stack = 0,
            isMoneyBetEnabled = false,
            smallBlind = 0,
            money = 0,
            isIncreaseBlindsEnabled = false,
            ratioStackMoney = 0,
            frequencyIncreasingBlind = 0
        )
        currentPlayer = Player(
            id = "",
            name = "",
            stack = settings.stack,
            stackBetTurn = 0,
            stackBetPartTurn = 0,
            statePlayer = StatePlayer.Playing,
            stateBlind = StateBlind.Nothing,
            actionPlayer = ActionPlayer.Nothing
        )
        currentStackTurn = 0
        currentMaxRaisePartTurn = 0
        currentStateTurn = StateTurn.PreFlop
        shouldIncreaseBlindNextTurn = false
        shouldStartTimer = false
        timeRemainingBeforeIncreaseBlinds = 0
    }

    fun addMoneyToPlayerBetweenTurn(playerCustomStack: PlayerCustomStack) {
        val player = playersList.find { it.id == playerCustomStack.id }
        if (player != null) {
            player.stack += playerCustomStack.stack
        }
    }

    fun withDrawMoneyToPlayerBetweenTurn(playerCustomStack: PlayerCustomStack) {
        val player = playersList.find { it.id == playerCustomStack.id }
        if (player != null) {
            player.stack -= playerCustomStack.stack
        }
    }

}
