package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.CustomStack
import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.PlayerCustomStack
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl

class AddOrWithdrawStackInteractor {

    fun execute(listener: Listener, playerList: List<PlayerCustomStack>) {
        playerList.forEach {
            if (it.customStack == CustomStack.Add) {
                GameRepositoryImpl.addMoneyToPlayerBetweenTurn(it)
            } else if (it.customStack == CustomStack.Withdraw) {
                GameRepositoryImpl.withDrawMoneyToPlayerBetweenTurn(it)
            }
        }
        listener.onSuccess(GameRepositoryImpl.listPlayers)
    }

    interface Listener {
        fun onSuccess(list: List<Player>)
    }
}
