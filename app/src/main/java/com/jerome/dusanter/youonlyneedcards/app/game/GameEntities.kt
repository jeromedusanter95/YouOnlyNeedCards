package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Pot

sealed class GameUiModel {

    data class ShowCurrentTurn(
        val actionPlayerList: List<ActionPlayer>,
        val informationsCurrentPlayer: String,
        val namePartTurn: String,
        val stackTurn: String
    ) : GameUiModel()

    data class ShowEndTurn(
        val potList: List<Pot>
    ) : GameUiModel()
}

data class DialogRaiseUiModel(
    val bigBlind: Int,
    val stackPlayer: Int
)
