package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.interactor.AddPlayerInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.StartGameInteractor

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

    fun onStartGame() {
        StartGameInteractor().execute(buildGameListener())
    }

    private fun buildGameListener(): StartGameInteractor.Listener =
        object : StartGameInteractor.Listener {
            override fun getPossibleActions(actionPlayerList: List<ActionPlayer>, playerList: List<Player>) {
                stateGame.value = GameMapper().map(actionPlayerList)
                playerList.forEach {
                    updatePlayerById(player = it)
                }
            }
        }
}
