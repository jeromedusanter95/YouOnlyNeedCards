package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.Player
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.StateTurn
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import com.jerome.dusanter.youonlyneedcards.utils.MutableCircularList

class PopulateGameWithFakeDataInteractor {

    fun execute(listener: Listener) {
        GameRepositoryImpl.currentStackTurn = 0
        GameRepositoryImpl.currentStateTurn = StateTurn.PreFlop
        val list = mutableListOf<Player>()
        list.add(Player("1", "Hugues", 2000))
        list.add(Player("2", "Jérôme", 2000))
        list.add(Player("3", "Gaëtan", 2000))
        list.add(Player("4", "Fred", 2000))
        list.add(Player("5", "Geof", 2000))
        list.add(Player("6", "Vincent", 2000))
        GameRepositoryImpl.listPlayers = MutableCircularList(list)
        GameRepositoryImpl.settings = Settings(
            2000,
            false,
            0,
            20,
            false,
            0
        )
        listener.onSuccess()
    }

    interface Listener {
        fun onSuccess()
    }
}
