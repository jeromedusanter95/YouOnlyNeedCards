package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.CustomStack
import com.jerome.dusanter.youonlyneedcards.core.Game
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.PlayerCustomStack
import javax.inject.Inject

class AddOrWithdrawStackInteractor @Inject internal constructor(){
    fun execute(listener: Listener, playerList: List<PlayerCustomStack>) {
        playerList.forEach {
            if (it.customStack == CustomStack.Add) {
                Game.addMoneyToPlayerBetweenTurn(it)
            } else if (it.customStack == CustomStack.Withdraw) {
                Game.withDrawMoneyToPlayerBetweenTurn(it)
            }
        }
        listener.onSuccess(Game.listPlayers)
    }

    interface Listener {
        fun onSuccess(list: List<Player>)
    }
}
