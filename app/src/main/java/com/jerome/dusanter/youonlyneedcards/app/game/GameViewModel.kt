package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.PotChooseWinners
import com.jerome.dusanter.youonlyneedcards.app.game.playerprofile.PlayerProfileMapper
import com.jerome.dusanter.youonlyneedcards.app.game.playerprofile.PlayerProfileUiModel
import com.jerome.dusanter.youonlyneedcards.core.*
import com.jerome.dusanter.youonlyneedcards.core.interactor.*
import javax.inject.Inject

class GameViewModel @Inject internal constructor(
    private val addPlayerInteractor: AddPlayerInteractor,
    private val startGameInteractor: StartGameInteractor,
    private val getParametersToRaiseInteractor: GetParametersToRaiseInteractor,
    private val playInteractor: PlayInteractor,
    private val startTurnInteractor: StartTurnInteractor,
    private val distributeStackInteractor: DistributeStackInteractor,
    private val checkIfGameOverInteractor: CheckIfGameOverInteractor,
    private val endGameInteractor: EndGameInteractor,
    private val increaseBlindsInteractor: IncreaseBlindsInteractor,
    private val rebuyPlayerInteractor: RebuyPlayerInteractor,
    private val getPlayerListAndInitialStackInteractor: GetPlayerListAndInitialStackInteractor,
    private val addOrWithdrawStackInteractor: AddOrWithdrawStackInteractor,
    private val deleteGameInteractor: DeleteGameInteractor,
    private val saveGameInInteractor: SaveGameInInteractor,
    private val gameMapper: GameMapper,
    private val playerProfileMapper: PlayerProfileMapper
) : ViewModel() {

    // Live data of ProfilePlayerView
    val statePlayerProfileView1 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView2 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView3 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView4 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView5 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView6 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView7 = MutableLiveData<PlayerProfileUiModel>()
    val statePlayerProfileView8 = MutableLiveData<PlayerProfileUiModel>()

    // Live data of Game
    val stateGame = MutableLiveData<GameUiModel>()

    //locale variables
    private var actionPlayersList = listOf<ActionPlayer>()
    private var timeRemainingBeforeIncreaseBlind: Long = 0

    fun onStart(booleanExtra: Boolean) {
        if (booleanExtra) {
            startTurn()
        }
    }

    fun onClickImageButtonCheckProfilePlayerView(id: String, name: String) {
        addPlayer(id, name)
    }

    fun onClickRebuyLayoutProfilePlayerView(id: String) {
        rebuyPlayer(id)
    }

    fun onClickButtonStartGame() {
        startGame()
    }

    fun onClickButtonStartTurn() {
        startTurn()
    }

    fun onTimerTick(timeRemainingBeforeIncreaseBlind: Long) {
        this.timeRemainingBeforeIncreaseBlind = timeRemainingBeforeIncreaseBlind
    }

    fun onTimerFinish() {
        increaseBlinds()
    }

    fun onClickButtonCustomStack() {
        customStack()
    }


    fun onClickButtonLeft() {
        val actionPlayer = mapToActionPlayer(PlayButton.LEFT)
        play(actionPlayer)
    }

    fun onClickButtonMiddle() {
        val actionPlayer = mapToActionPlayer(PlayButton.MIDDLE)
        play(actionPlayer)
    }

    fun onClickButtonRight() {
        try {
            val actionPlayer = mapToActionPlayer(PlayButton.RIGHT)
            play(actionPlayer)
        } catch (e: Exception) {
            Log.d("GameViewModel", e.message)
        }
    }

    fun notShowChooseWinnersDialog(potChooseWinners: PotChooseWinners) {
        distributeStack(
            potChooseWinners.potentialWinnerList.map {
                Winner(it.id, potChooseWinners.stack)
            }
        )
    }

    fun onShowEndTurnDialog() {
        saveGame(timeRemainingBeforeIncreaseBlind)
    }

    fun onDismissRaiseDialog(actionPlayer: ActionPlayer, stackRaised: Int) {
        play(actionPlayer, stackRaised)
    }

    fun onDismissCustomStackDialog(playerList: List<PlayerCustomStackUiModel>) {
        addOrWithdrawStack(playerList)
    }

    fun onDismissChooseWinnersDialog(winnerList: List<Winner>) {
        distributeStack(winnerList)
    }

    fun onDismissEndTurnDialog() {
        checkIfGameOver()
    }

    fun onDismissConfirmationEndGameDialog() {
        endGame()
    }

    fun onDismissEndGameDialog() {
        deleteGame()
    }

    private fun addPlayer(id: String, name: String) {
        addPlayerInteractor.execute(id, name, buildAddPlayerListener())
    }

    private fun buildAddPlayerListener(): AddPlayerInteractor.Listener =
        object : AddPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player)
            }
        }

    private fun updatePlayerById(player: Player) {
        when (player.id) {
            "1" -> statePlayerProfileView1.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "2" -> statePlayerProfileView2.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "3" -> statePlayerProfileView3.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "4" -> statePlayerProfileView4.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "5" -> statePlayerProfileView5.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "6" -> statePlayerProfileView6.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "7" -> statePlayerProfileView7.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
            "8" -> statePlayerProfileView8.value =
                playerProfileMapper.mapToProfilePlayerUiModel(player)
        }
    }

    private fun showRebuyById(player: Player) {
        when (player.id) {
            "1" -> statePlayerProfileView1.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "2" -> statePlayerProfileView2.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "3" -> statePlayerProfileView3.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "4" -> statePlayerProfileView4.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "5" -> statePlayerProfileView5.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "6" -> statePlayerProfileView6.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "7" -> statePlayerProfileView7.value = PlayerProfileUiModel.ShowRebuy(player.name)
            "8" -> statePlayerProfileView8.value = PlayerProfileUiModel.ShowRebuy(player.name)
        }
    }

    private fun updatePlayerOrHidePlayer(list: List<Player>) {
        val player1 = list.find { it.id == "1" }
        if (player1 != null) {
            statePlayerProfileView1.value = playerProfileMapper.mapToProfilePlayerUiModel(player1)
        } else {
            statePlayerProfileView1.value = null
        }

        val player2 = list.find { it.id == "2" }
        if (player2 != null) {
            statePlayerProfileView2.value = playerProfileMapper.mapToProfilePlayerUiModel(player2)
        } else {
            statePlayerProfileView2.value = null
        }

        val player3 = list.find { it.id == "3" }
        if (player3 != null) {
            statePlayerProfileView3.value = playerProfileMapper.mapToProfilePlayerUiModel(player3)
        } else {
            statePlayerProfileView3.value = null
        }

        val player4 = list.find { it.id == "4" }
        if (player4 != null) {
            statePlayerProfileView4.value = playerProfileMapper.mapToProfilePlayerUiModel(player4)
        } else {
            statePlayerProfileView4.value = null
        }

        val player5 = list.find { it.id == "5" }
        if (player5 != null) {
            statePlayerProfileView5.value = playerProfileMapper.mapToProfilePlayerUiModel(player5)
        } else {
            statePlayerProfileView5.value = null
        }

        val player6 = list.find { it.id == "6" }
        if (player6 != null) {
            statePlayerProfileView6.value = playerProfileMapper.mapToProfilePlayerUiModel(player6)
        } else {
            statePlayerProfileView6.value = null
        }

        val player7 = list.find { it.id == "7" }
        if (player7 != null) {
            statePlayerProfileView7.value = playerProfileMapper.mapToProfilePlayerUiModel(player7)
        } else {
            statePlayerProfileView7.value = null
        }

        val player8 = list.find { it.id == "8" }
        if (player8 != null) {
            statePlayerProfileView8.value = playerProfileMapper.mapToProfilePlayerUiModel(player8)
        } else {
            statePlayerProfileView8.value = null
        }
    }

    private fun startGame() {
        startGameInteractor.execute(buildGameListener())
    }

    private fun buildGameListener(): StartGameInteractor.Listener =
        object : StartGameInteractor.Listener {
            override fun onSuccess(response: StartGameInteractor.Response) {
                actionPlayersList = response.actionPlayerList
                stateGame.value = gameMapper.mapShowCurrentTurn(
                    actionPlayerList = response.actionPlayerList,
                    namePartTurn = StateTurn.PreFlop.name,
                    stackTurn = response.stackTurn,
                    nameCurrentPlayer = response.currentPlayer.name,
                    stackCurrentPlayer = response.currentPlayer.stack,
                    resetTimer = response.resetTimer,
                    durationBeforeIncreasingBlinds = response.durationBeforeIncreasingBlinds
                )
                updatePlayerOrHidePlayer(response.playersList)
            }

            override fun onError() {
                stateGame.value = GameUiModel.ShowErrorNotEnoughtPlayer
            }
        }

    private fun startTurn() {
        startTurnInteractor.execute(buildStartTurnListener())
    }

    private fun buildStartTurnListener(): StartTurnInteractor.Listener =
        object : StartTurnInteractor.Listener {
            override fun onSuccess(response: StartTurnInteractor.Response) {
                actionPlayersList = response.actionPlayerList
                stateGame.value = gameMapper.mapShowCurrentTurn(
                    actionPlayerList = response.actionPlayerList,
                    namePartTurn = response.stateTurn.name,
                    stackTurn = response.stackTurn,
                    nameCurrentPlayer = response.currentPlayer.name,
                    stackCurrentPlayer = response.currentPlayer.stack,
                    resetTimer = response.resetTimer,
                    durationBeforeIncreasingBlinds = response.durationBeforeIncreasingBlinds
                )
                updatePlayerOrHidePlayer(response.playerList)
            }
        }

    private fun play(actionPlayer: ActionPlayer) {
        if (actionPlayer != ActionPlayer.Raise) {
            playInteractor.execute(PlayInteractor.Request(actionPlayer), buildPlayListener())
        } else {
            getParametersToRaiseInteractor.execute(buildGetBigBlindListener())
        }
    }

    private fun play(actionPlayer: ActionPlayer, stackRaised: Int) {
        playInteractor.execute(
            PlayInteractor.Request(actionPlayer, stackRaised),
            buildPlayListener()
        )
    }

    private fun mapToActionPlayer(playButton: PlayButton): ActionPlayer {
        return if (actionPlayersList.size == 3) {
            when (playButton) {
                PlayButton.LEFT -> actionPlayersList[0]
                PlayButton.MIDDLE -> actionPlayersList[1]
                PlayButton.RIGHT -> actionPlayersList[2]
            }
        } else {
            when (playButton) {
                PlayButton.LEFT -> actionPlayersList[0]
                PlayButton.MIDDLE -> actionPlayersList[1]
                else -> throw Exception("Button right can't be clicked in this situation")
            }
        }
    }

    private fun buildGetBigBlindListener(): GetParametersToRaiseInteractor.Listener =
        object : GetParametersToRaiseInteractor.Listener {
            override fun onSuccess(response: GetParametersToRaiseInteractor.Response) {
                stateGame.value = gameMapper.mapToRaiseDialog(
                    bigBlind = response.bigBlind,
                    stackPlayer = response.stackPlayer
                )
            }
        }

    private fun buildPlayListener(): PlayInteractor.Listener =
        object : PlayInteractor.Listener {
            override fun onSuccess(response: PlayInteractor.Response) {
                if (!response.isEndTurn) {
                    actionPlayersList = response.actionPlayerList
                    stateGame.value = gameMapper.mapShowCurrentTurn(
                        actionPlayerList = response.actionPlayerList,
                        nameCurrentPlayer = response.currentPlayer.name,
                        stackCurrentPlayer = response.currentPlayer.stack,
                        namePartTurn = response.stateTurn.name,
                        stackTurn = response.stackTurn
                    )
                    response.playerList.forEach {
                        updatePlayerById(it)
                    }
                } else {
                    stateGame.value = gameMapper.mapToShowChooseWinnersDialog(
                        response.potList
                    )
                }
            }
        }

    private fun distributeStack(winnerList: List<Winner>) {
        distributeStackInteractor.execute(winnerList, buildDistributeStackListener())
    }

    private fun buildDistributeStackListener(): DistributeStackInteractor.Listener =
        object : DistributeStackInteractor.Listener {
            override fun onSuccess(response: DistributeStackInteractor.Response) {
                stateGame.value = gameMapper.mapToEndTurnDialog(response.playersEndTurnList)
                response.playersList.forEach {
                    if (it.statePlayer == StatePlayer.Eliminate) {
                        showRebuyById(it)
                    } else {
                        updatePlayerById(it)
                    }
                }
            }
        }


    private fun checkIfGameOver() {
        checkIfGameOverInteractor.execute(buildCheckIfGameOverListener())
    }

    private fun buildCheckIfGameOverListener(): CheckIfGameOverInteractor.Listener =
        object : CheckIfGameOverInteractor.Listener {
            override fun onSuccess(response: CheckIfGameOverInteractor.Response) {
                if (response.isGameOver) {
                    stateGame.value = gameMapper.mapEndGameDialog(
                        playerEndGameList = response.playerEndGameList,
                        settings = response.settings
                    )
                }
            }
        }

    private fun endGame() {
        endGameInteractor.execute(buildEndGameInteractor())
    }

    private fun buildEndGameInteractor(): EndGameInteractor.Listener =
        object : EndGameInteractor.Listener {
            override fun onEndGame(response: EndGameInteractor.Response) {
                stateGame.value = gameMapper.mapEndGameDialog(
                    playerEndGameList = response.playerEndGameList,
                    settings = response.settings
                )
            }
        }

    private fun increaseBlinds() {
        increaseBlindsInteractor.execute()
    }

    private fun saveGame(timeRemainingBeforeIncreaseBlind: Long) {
        saveGameInInteractor.execute(timeRemainingBeforeIncreaseBlind)
    }

    private fun deleteGame() {
        deleteGameInteractor.execute()
    }

    private fun rebuyPlayer(playerId: String) {
        rebuyPlayerInteractor.execute(buildRebuyPlayerListener(), playerId)
    }

    private fun buildRebuyPlayerListener(): RebuyPlayerInteractor.Listener =
        object : RebuyPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player)
            }
        }

    private fun customStack() {
        getPlayerListAndInitialStackInteractor.execute(
            buildGetPlayerListAndInitialStackInteractor()
        )
    }

    private fun buildGetPlayerListAndInitialStackInteractor(): GetPlayerListAndInitialStackInteractor.Listener =
        object : GetPlayerListAndInitialStackInteractor.Listener {
            override fun onSuccess(playerList: List<Player>) {
                stateGame.value = gameMapper.mapToShowCustomStackDialog(playerList)
            }
        }

    private fun addOrWithdrawStack(playerList: List<PlayerCustomStackUiModel>) {
        addOrWithdrawStackInteractor.execute(
            buildAddOrWithdrawListener(),
            gameMapper.mapToCustomStack(playerList)
        )
    }

    private fun buildAddOrWithdrawListener(): AddOrWithdrawStackInteractor.Listener =
        object : AddOrWithdrawStackInteractor.Listener {
            override fun onSuccess(list: List<Player>) {
                list.forEach {
                    updatePlayerById(it)
                }
            }
        }

    enum class PlayButton {
        LEFT, MIDDLE, RIGHT
    }
}
