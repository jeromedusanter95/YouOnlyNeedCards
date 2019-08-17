package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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

    fun onAddPlayer(id: String, name: String) {
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
            "1" -> statePlayerProfileView1.value = playerProfileMapper.map(player)
            "2" -> statePlayerProfileView2.value = playerProfileMapper.map(player)
            "3" -> statePlayerProfileView3.value = playerProfileMapper.map(player)
            "4" -> statePlayerProfileView4.value = playerProfileMapper.map(player)
            "5" -> statePlayerProfileView5.value = playerProfileMapper.map(player)
            "6" -> statePlayerProfileView6.value = playerProfileMapper.map(player)
            "7" -> statePlayerProfileView7.value = playerProfileMapper.map(player)
            "8" -> statePlayerProfileView8.value = playerProfileMapper.map(player)
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
            statePlayerProfileView1.value = playerProfileMapper.map(player1)
        } else {
            statePlayerProfileView1.value = null
        }

        val player2 = list.find { it.id == "2" }
        if (player2 != null) {
            statePlayerProfileView2.value = playerProfileMapper.map(player2)
        } else {
            statePlayerProfileView2.value = null
        }

        val player3 = list.find { it.id == "3" }
        if (player3 != null) {
            statePlayerProfileView3.value = playerProfileMapper.map(player3)
        } else {
            statePlayerProfileView3.value = null
        }

        val player4 = list.find { it.id == "4" }
        if (player4 != null) {
            statePlayerProfileView4.value = playerProfileMapper.map(player4)
        } else {
            statePlayerProfileView4.value = null
        }

        val player5 = list.find { it.id == "5" }
        if (player5 != null) {
            statePlayerProfileView5.value = playerProfileMapper.map(player5)
        } else {
            statePlayerProfileView5.value = null
        }

        val player6 = list.find { it.id == "6" }
        if (player6 != null) {
            statePlayerProfileView6.value = playerProfileMapper.map(player6)
        } else {
            statePlayerProfileView6.value = null
        }

        val player7 = list.find { it.id == "7" }
        if (player7 != null) {
            statePlayerProfileView7.value = playerProfileMapper.map(player7)
        } else {
            statePlayerProfileView7.value = null
        }

        val player8 = list.find { it.id == "8" }
        if (player8 != null) {
            statePlayerProfileView8.value = playerProfileMapper.map(player8)
        } else {
            statePlayerProfileView8.value = null
        }
    }

    fun onStartGame() {
        startGameInteractor.execute(buildGameListener())
    }

    private fun buildGameListener(): StartGameInteractor.Listener =
        object : StartGameInteractor.Listener {
            override fun onError() {
                stateGame.value = GameUiModel.ShowErrorNotEnoughtPlayer
            }

            override fun onSuccess(
                actionPlayerList: List<ActionPlayer>,
                playerList: List<Player>,
                stackTurn: Int,
                resetTimer: Boolean,
                durationBeforeIncreasingBlind: Long
            ) {
                val currentPlayer = playerList.find { it.statePlayer == StatePlayer.CurrentTurn }
                stateGame.value = gameMapper.map(
                    actionPlayerList = actionPlayerList,
                    namePartTurn = StateTurn.PreFlop.name,
                    stackTurn = stackTurn,
                    nameCurrentPlayer = currentPlayer!!.name,
                    stackCurrentPlayer = currentPlayer.stack,
                    resetTimer = resetTimer,
                    durationBeforeIncreasingBlinds = durationBeforeIncreasingBlind
                )
                updatePlayerOrHidePlayer(playerList)
            }
        }

    fun onClickButtonLeft(actionPlayer: String?) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            getParametersToRaiseInteractor.execute(buildGetBigBlindListener())
        }
    }

    fun onClickButtonMiddle(actionPlayer: String?) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            getParametersToRaiseInteractor.execute(buildGetBigBlindListener())
        }
    }

    fun onClickButtonRight(actionPlayer: String?) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            getParametersToRaiseInteractor.execute(buildGetBigBlindListener())
        }
    }

    private fun buildGetBigBlindListener(): GetParametersToRaiseInteractor.Listener =
        object : GetParametersToRaiseInteractor.Listener {
            override fun onSuccess(bigBlind: Int, stackPlayer: Int) {
                stateGame.value = gameMapper.map(bigBlind, stackPlayer)
            }
        }

    private fun play(actionPlayer: String) {
        playInteractor.execute(PlayRequest(actionPlayer), buildPlayListener())
    }

    fun play(actionPlayer: String, stackRaised: Int) {
        playInteractor.execute(PlayRequest(actionPlayer, stackRaised), buildPlayListener())
    }

    private fun buildPlayListener(): PlayInteractor.Listener =
        object : PlayInteractor.Listener {
            override fun getGameInformations(
                actionPlayerList: List<ActionPlayer>,
                playerList: List<Player>,
                stackTurn: Int,
                stateTurn: StateTurn,
                isEndTurn: Boolean,
                potList: List<Pot>
            ) {
                if (!isEndTurn) {
                    val currentPlayer = playerList.find { it.statePlayer == StatePlayer.CurrentTurn }
                    stateGame.value = gameMapper.map(
                        actionPlayerList = actionPlayerList,
                        nameCurrentPlayer = currentPlayer!!.name,
                        stackCurrentPlayer = currentPlayer.stack,
                        namePartTurn = stateTurn.name,
                        stackTurn = stackTurn
                    )
                    playerList.forEach {
                        updatePlayerById(it)
                    }
                } else {
                    stateGame.value = gameMapper.map(
                        potList
                    )
                }
            }
        }

    fun onStartTurn() {
        startTurnInteractor.execute(buildStartTurnListener())
    }

    private fun buildStartTurnListener(): StartTurnInteractor.Listener =
        object : StartTurnInteractor.Listener {
            override fun getPossibleActions(
                actionPlayerList: List<ActionPlayer>,
                playerList: List<Player>,
                stackTurn: Int,
                stateTurn: StateTurn,
                resetTimer: Boolean,
                durationBeforeIncreasingBlinds: Long
            ) {
                val currentPlayer = playerList.find { it.statePlayer == StatePlayer.CurrentTurn }
                stateGame.value = gameMapper.map(
                    actionPlayerList,
                    currentPlayer!!.name,
                    currentPlayer.stack,
                    stateTurn.name,
                    stackTurn,
                    resetTimer,
                    durationBeforeIncreasingBlinds
                )
                updatePlayerOrHidePlayer(playerList)
            }
        }

    fun onDistributeStack(potUiModel: PotUiModel) {
        distributeStackInteractor.execute(gameMapper.map(potUiModel), buildDistributeStackListener())
    }

    fun onDistributeStack(winnerList: List<Winner>) {
        distributeStackInteractor.execute(winnerList, buildDistributeStackListener())
    }

    private fun buildDistributeStackListener(): DistributeStackInteractor.Listener =
        object : DistributeStackInteractor.Listener {
            override fun onSuccess(playerEndTurnList: List<PlayerEndTurn>, playerList: List<Player>) {
                stateGame.value = gameMapper.map(playerEndTurnList)
                playerList.forEach {
                    if (it.statePlayer == StatePlayer.Eliminate) {
                        showRebuyById(it)
                    } else {
                        updatePlayerById(it)
                    }
                }
            }
        }


    fun onCheckIfGameOver() {
        checkIfGameOverInteractor.execute(buildCheckIfGameOverListener())
    }

    private fun buildCheckIfGameOverListener(): CheckIfGameOverInteractor.Listener =
        object : CheckIfGameOverInteractor.Listener {
            override fun onSuccess(isGameOver: Boolean, playerEndGameList: List<PlayerEndGame>?, settings: Settings) {
                if (isGameOver) {
                    stateGame.value = gameMapper.map(
                        playerEndGameList = playerEndGameList!!.toMutableList(),
                        settings = settings
                    )
                }
            }
        }

    fun onEndGame() {
        endGameInteractor.execute(buildEndGameInteractor())
    }

    private fun buildEndGameInteractor(): EndGameInteractor.Listener =
        object : EndGameInteractor.Listener {
            override fun onEndGame(playerEndGameList: List<PlayerEndGame>?, settings: Settings) {
                stateGame.value = gameMapper.map(
                    playerEndGameList = playerEndGameList!!.toMutableList(),
                    settings = settings
                )
            }
        }

    fun increaseBlinds() {
        increaseBlindsInteractor.execute()
    }

    fun saveGame(timeRemainingBeforeIncreaseBlind: Long) {
        saveGameInInteractor.execute(timeRemainingBeforeIncreaseBlind)
    }

    fun deleteGame() {
        deleteGameInteractor.execute()
    }

    fun onRebuyPlayer(playerId: String) {
        rebuyPlayerInteractor.execute(buildRebuyPlayerListener(), playerId)
    }

    private fun buildRebuyPlayerListener(): RebuyPlayerInteractor.Listener =
        object : RebuyPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player)
            }
        }

    fun onCustomStack() {
        getPlayerListAndInitialStackInteractor.execute(buildGetPlayerListAndInitialStackInteractor())
    }

    private fun buildGetPlayerListAndInitialStackInteractor(): GetPlayerListAndInitialStackInteractor.Listener =
        object : GetPlayerListAndInitialStackInteractor.Listener {
            override fun onSuccess(playerList: List<Player>) {
                stateGame.value = gameMapper.mapToShowCustomStackDialog(playerList)
            }
        }

    fun onAddOrWithdrawStack(playerList: List<PlayerCustomStackUiModel>) {
        addOrWithdrawStackInteractor.execute(buildAddOrWithdrawListener(), gameMapper.mapToCustomStack(playerList))
    }

    private fun buildAddOrWithdrawListener(): AddOrWithdrawStackInteractor.Listener =
        object : AddOrWithdrawStackInteractor.Listener {
            override fun onSuccess(list: List<Player>) {
                list.forEach {
                    updatePlayerById(it)
                }
            }
        }
}
