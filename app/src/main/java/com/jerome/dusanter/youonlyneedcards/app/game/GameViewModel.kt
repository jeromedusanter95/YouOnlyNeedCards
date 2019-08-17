package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerProfile.PlayerProfileMapper
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerProfile.PlayerProfileUiModel
import com.jerome.dusanter.youonlyneedcards.core.*
import com.jerome.dusanter.youonlyneedcards.core.interactor.*
import javax.inject.Inject

class GameViewModel @Inject internal constructor(
    private val deleteGameInteractor: DeleteGameInteractor,
    val saveGameInInteractor: SaveGameInInteractor
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

    fun onAddPlayer(id: String, name: String, context: Context) {
        AddPlayerInteractor().execute(id, name, buildAddPlayerListener(context))
    }

    private fun buildAddPlayerListener(context: Context): AddPlayerInteractor.Listener =
        object : AddPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player, context)
            }
        }

    private fun updatePlayerById(player: Player, context: Context) {
        when (player.id) {
            "1" -> statePlayerProfileView1.value = PlayerProfileMapper().map(player, context)
            "2" -> statePlayerProfileView2.value = PlayerProfileMapper().map(player, context)
            "3" -> statePlayerProfileView3.value = PlayerProfileMapper().map(player, context)
            "4" -> statePlayerProfileView4.value = PlayerProfileMapper().map(player, context)
            "5" -> statePlayerProfileView5.value = PlayerProfileMapper().map(player, context)
            "6" -> statePlayerProfileView6.value = PlayerProfileMapper().map(player, context)
            "7" -> statePlayerProfileView7.value = PlayerProfileMapper().map(player, context)
            "8" -> statePlayerProfileView8.value = PlayerProfileMapper().map(player, context)
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

    private fun updatePlayerOrHidePlayer(list: List<Player>, context: Context) {
        val player1 = list.find { it.id == "1" }
        if (player1 != null) {
            statePlayerProfileView1.value = PlayerProfileMapper().map(player1, context)
        } else {
            statePlayerProfileView1.value = null
        }

        val player2 = list.find { it.id == "2" }
        if (player2 != null) {
            statePlayerProfileView2.value = PlayerProfileMapper().map(player2, context)
        } else {
            statePlayerProfileView2.value = null
        }

        val player3 = list.find { it.id == "3" }
        if (player3 != null) {
            statePlayerProfileView3.value = PlayerProfileMapper().map(player3, context)
        } else {
            statePlayerProfileView3.value = null
        }

        val player4 = list.find { it.id == "4" }
        if (player4 != null) {
            statePlayerProfileView4.value = PlayerProfileMapper().map(player4, context)
        } else {
            statePlayerProfileView4.value = null
        }

        val player5 = list.find { it.id == "5" }
        if (player5 != null) {
            statePlayerProfileView5.value = PlayerProfileMapper().map(player5, context)
        } else {
            statePlayerProfileView5.value = null
        }

        val player6 = list.find { it.id == "6" }
        if (player6 != null) {
            statePlayerProfileView6.value = PlayerProfileMapper().map(player6, context)
        } else {
            statePlayerProfileView6.value = null
        }

        val player7 = list.find { it.id == "7" }
        if (player7 != null) {
            statePlayerProfileView7.value = PlayerProfileMapper().map(player7, context)
        } else {
            statePlayerProfileView7.value = null
        }

        val player8 = list.find { it.id == "8" }
        if (player8 != null) {
            statePlayerProfileView8.value = PlayerProfileMapper().map(player8, context)
        } else {
            statePlayerProfileView8.value = null
        }
    }

    fun onStartGame(context: Context) {
        StartGameInteractor().execute(buildGameListener(context))
    }

    private fun buildGameListener(context: Context): StartGameInteractor.Listener =
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
                stateGame.value = GameMapper().map(
                    actionPlayerList = actionPlayerList,
                    namePartTurn = StateTurn.PreFlop.name,
                    stackTurn = stackTurn,
                    nameCurrentPlayer = currentPlayer!!.name,
                    stackCurrentPlayer = currentPlayer.stack,
                    resetTimer = resetTimer,
                    durationBeforeIncreasingBlinds = durationBeforeIncreasingBlind,
                    context = context
                )
                updatePlayerOrHidePlayer(playerList, context)
            }
        }

    fun onClickButtonLeft(actionPlayer: String?, context: Context) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer, context)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            GetParametersToRaiseInteractor().execute(buildGetBigBlindInteractor())
        }
    }

    fun onClickButtonMiddle(actionPlayer: String?, context: Context) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer, context)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            GetParametersToRaiseInteractor().execute(buildGetBigBlindInteractor())
        }
    }

    fun onClickButtonRight(actionPlayer: String?, context: Context) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer, context)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            GetParametersToRaiseInteractor().execute(buildGetBigBlindInteractor())
        }
    }

    private fun buildGetBigBlindInteractor(): GetParametersToRaiseInteractor.Listener =
        object : GetParametersToRaiseInteractor.Listener {
            override fun onSuccess(bigBlind: Int, stackPlayer: Int) {
                stateGame.value = GameMapper().map(bigBlind, stackPlayer)
            }
        }

    private fun play(actionPlayer: String, context: Context) {
        PlayInteractor().execute(PlayRequest(actionPlayer), buildPlayListener(context))
    }

    fun play(actionPlayer: String, stackRaised: Int, context: Context) {
        PlayInteractor().execute(PlayRequest(actionPlayer, stackRaised), buildPlayListener(context))
    }

    private fun buildPlayListener(context: Context): PlayInteractor.Listener =
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
                    stateGame.value = GameMapper().map(
                        actionPlayerList = actionPlayerList,
                        nameCurrentPlayer = currentPlayer!!.name,
                        stackCurrentPlayer = currentPlayer.stack,
                        namePartTurn = stateTurn.name,
                        stackTurn = stackTurn,
                        context = context
                    )
                    playerList.forEach {
                        updatePlayerById(it, context)
                    }
                } else {
                    stateGame.value = GameMapper().map(
                        potList
                    )
                }
            }
        }

    fun onStartTurn(context: Context) {
        StartTurnInteractor().execute(buildStartTurnListener(context))
    }

    private fun buildStartTurnListener(context: Context): StartTurnInteractor.Listener =
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
                stateGame.value = GameMapper().map(
                    actionPlayerList,
                    currentPlayer!!.name,
                    currentPlayer.stack,
                    stateTurn.name,
                    stackTurn,
                    resetTimer,
                    durationBeforeIncreasingBlinds,
                    context
                )
                updatePlayerOrHidePlayer(playerList, context)
            }
        }

    fun onDistributeStack(winnerList: List<Winner>, context: Context) {
        DistributeStackInteractor().execute(winnerList, buildDistributeStackListener(context))
    }

    private fun buildDistributeStackListener(context: Context): DistributeStackInteractor.Listener =
        object : DistributeStackInteractor.Listener {
            override fun onSuccess(
                playerEndTurnList: List<PlayerEndTurn>,
                playerList: List<Player>
            ) {
                stateGame.value = GameMapper().map(playerEndTurnList, context)
                playerList.forEach {
                    if (it.statePlayer == StatePlayer.Eliminate) {
                        showRebuyById(it)
                    } else {
                        updatePlayerById(it, context)
                    }
                }
            }
        }


    fun onCheckIfGameOver(context: Context) {
        CheckIfGameOverInteractor().execute(buildCheckIfGameOverListener(context))
    }

    private fun buildCheckIfGameOverListener(context: Context): CheckIfGameOverInteractor.Listener =
        object : CheckIfGameOverInteractor.Listener {
            override fun onSuccess(
                isGameOver: Boolean,
                playerEndGameList: List<PlayerEndGame>?,
                settings: Settings
            ) {
                if (isGameOver) {
                    stateGame.value = GameMapper().map(
                        playerEndGameList = playerEndGameList!!.toMutableList(),
                        settings = settings,
                        context = context
                    )
                }
            }
        }

    fun onEndGame(context: Context) {
        EndGameInteractor().execute(buildEndGameInteractor(context))
    }

    private fun buildEndGameInteractor(context: Context): EndGameInteractor.Listener =
        object : EndGameInteractor.Listener {
            override fun onEndGame(
                playerEndGameList: List<PlayerEndGame>?,
                settings: Settings
            ) {
                stateGame.value = GameMapper().map(
                    playerEndGameList = playerEndGameList!!.toMutableList(),
                    context = context,
                    settings = settings
                )
            }
        }

    fun increaseBlinds() {
        IncreaseBlindsInteractor().execute()
    }

    fun saveGame(timeRemainingBeforeIncreaseBlind: Long) {
        saveGameInInteractor.execute(timeRemainingBeforeIncreaseBlind)
    }

    fun deleteGame() {
        deleteGameInteractor.execute()
    }

    fun onRebuyPlayer(playerId: String, context: Context) {
        RebuyPlayerInteractor().execute(buildRebuyPlayerListener(context), playerId)
    }

    private fun buildRebuyPlayerListener(context: Context): RebuyPlayerInteractor.Listener =
        object : RebuyPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player, context)
            }
        }

    fun onCustomStack() {
        GetPlayerListAndInitialStackInteractor().execute(buildGetPlayerListAndInitialStackInteractor())
    }

    private fun buildGetPlayerListAndInitialStackInteractor(): GetPlayerListAndInitialStackInteractor.Listener =
        object : GetPlayerListAndInitialStackInteractor.Listener {
            override fun onSuccess(playerList: List<Player>) {
                stateGame.value = GameMapper().mapToShowCustomStackDialog(playerList)
            }
        }

    fun onAddOrWithdrawStack(playerList: List<PlayerCustomStackUiModel>, context: Context) {
        AddOrWithdrawStackInteractor().execute(
            buildAddOrWithdrawListener(context),
            GameMapper().mapToCustomStack(playerList)
        )
    }

    private fun buildAddOrWithdrawListener(context: Context): AddOrWithdrawStackInteractor.Listener =
        object : AddOrWithdrawStackInteractor.Listener {
            override fun onSuccess(list: List<Player>) {
                list.forEach {
                    updatePlayerById(it, context)
                }
            }
        }
}
