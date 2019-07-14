package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndTurn
import com.jerome.dusanter.youonlyneedcards.core.Pot
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.StatePlayer
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.core.Winner
import com.jerome.dusanter.youonlyneedcards.core.interactor.AddPlayerInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.CheckIfGameOverInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.DeleteGameInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.DistributeStackInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.EndGameInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.GetParametersToRaiseInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.IncreaseBlindsInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.PlayInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.PlayRequest
import com.jerome.dusanter.youonlyneedcards.core.interactor.SaveGameInInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.StartGameInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.StartTurnInteractor

class GameViewModel : ViewModel() {

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
        AddPlayerInteractor().execute(id, name, buildAddPlayerListener())
    }

    private fun buildAddPlayerListener(): AddPlayerInteractor.Listener =
        object : AddPlayerInteractor.Listener {
            override fun onSuccess(player: Player) {
                updatePlayerById(player)
            }
        }

    private fun updatePlayerById(player: Player) {
        when (player.id) {
            "1" -> statePlayerProfileView1.value = PlayerProfileMapper().map(player)
            "2" -> statePlayerProfileView2.value = PlayerProfileMapper().map(player)
            "3" -> statePlayerProfileView3.value = PlayerProfileMapper().map(player)
            "4" -> statePlayerProfileView4.value = PlayerProfileMapper().map(player)
            "5" -> statePlayerProfileView5.value = PlayerProfileMapper().map(player)
            "6" -> statePlayerProfileView6.value = PlayerProfileMapper().map(player)
            "7" -> statePlayerProfileView7.value = PlayerProfileMapper().map(player)
            "8" -> statePlayerProfileView8.value = PlayerProfileMapper().map(player)
        }
    }

    private fun updatePlayerOrHidePlayer(list: List<Player>) {
        val player1 = list.find { it.id == "1" }
        if (player1 != null) {
            statePlayerProfileView1.value = PlayerProfileMapper().map(player1)
        } else {
            statePlayerProfileView1.value = null
        }

        val player2 = list.find { it.id == "2" }
        if (player2 != null) {
            statePlayerProfileView2.value = PlayerProfileMapper().map(player2)
        } else {
            statePlayerProfileView2.value = null
        }

        val player3 = list.find { it.id == "3" }
        if (player3 != null) {
            statePlayerProfileView3.value = PlayerProfileMapper().map(player3)
        } else {
            statePlayerProfileView3.value = null
        }

        val player4 = list.find { it.id == "4" }
        if (player4 != null) {
            statePlayerProfileView4.value = PlayerProfileMapper().map(player4)
        } else {
            statePlayerProfileView4.value = null
        }

        val player5 = list.find { it.id == "5" }
        if (player5 != null) {
            statePlayerProfileView5.value = PlayerProfileMapper().map(player5)
        } else {
            statePlayerProfileView5.value = null
        }

        val player6 = list.find { it.id == "6" }
        if (player6 != null) {
            statePlayerProfileView6.value = PlayerProfileMapper().map(player6)
        } else {
            statePlayerProfileView6.value = null
        }

        val player7 = list.find { it.id == "7" }
        if (player7 != null) {
            statePlayerProfileView7.value = PlayerProfileMapper().map(player7)
        } else {
            statePlayerProfileView7.value = null
        }

        val player8 = list.find { it.id == "8" }
        if (player8 != null) {
            statePlayerProfileView8.value = PlayerProfileMapper().map(player8)
        } else {
            statePlayerProfileView8.value = null
        }
    }

    fun onStartGame() {
        StartGameInteractor().execute(buildGameListener())
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
                stateGame.value = GameMapper().map(
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
            GetParametersToRaiseInteractor().execute(buildGetBigBlindInteractor())
        }
    }

    fun onClickButtonMiddle(actionPlayer: String?) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer)
        } else if (actionPlayer == ActionPlayer.Raise.name) {
            GetParametersToRaiseInteractor().execute(buildGetBigBlindInteractor())
        }
    }

    fun onClickButtonRight(actionPlayer: String?) {
        if (actionPlayer != null && actionPlayer != ActionPlayer.Raise.name) {
            play(actionPlayer)
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

    private fun play(actionPlayer: String) {
        PlayInteractor().execute(PlayRequest(actionPlayer), buildPlayListener())
    }

    fun play(actionPlayer: String, stackRaised: Int) {
        PlayInteractor().execute(PlayRequest(actionPlayer, stackRaised), buildPlayListener())
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
                    stateGame.value = GameMapper().map(
                        actionPlayerList,
                        currentPlayer!!.name,
                        currentPlayer.stack,
                        stateTurn.name,
                        stackTurn
                    )
                    playerList.forEach {
                        updatePlayerById(it)
                    }
                } else {
                    stateGame.value = GameMapper().map(
                        potList
                    )
                }
            }
        }

    fun onStartTurn() {
        StartTurnInteractor().execute(buildStartTurnListener())
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
                stateGame.value = GameMapper().map(
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

    fun onDistributeStack(winnerList: List<Winner>) {
        DistributeStackInteractor().execute(winnerList, buildDistributeStackListener())
    }

    private fun buildDistributeStackListener(): DistributeStackInteractor.Listener =
        object : DistributeStackInteractor.Listener {
            override fun onSuccess(
                playerEndTurnList: List<PlayerEndTurn>,
                playerList: List<Player>
            ) {
                stateGame.value = GameMapper().map(playerEndTurnList)
                playerList.forEach {
                    updatePlayerById(it)
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

    fun saveGame(context: Context, timeRemainingBeforeIncreaseBlind: Long) {
        SaveGameInInteractor().execute(context, timeRemainingBeforeIncreaseBlind)
    }

    fun deleteGame(context: Context) {
        DeleteGameInteractor().execute(context)
    }
}
