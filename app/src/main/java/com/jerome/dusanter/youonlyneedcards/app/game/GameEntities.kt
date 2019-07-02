package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Winner

sealed class GameUiModel {
    data class ShowCurrentTurn(
        val actionPlayerList: List<ActionPlayer>,
        val informationsCurrentPlayer: String,
        val namePartTurn: String,
        val stackTurn: String
    ) : GameUiModel()

    data class ShowEndTurn(
        val winnerList: List<Winner>
    ) : GameUiModel()
}

data class DialogRaiseUiModel(
    val bigBlind: Int,
    val stackPlayer: Int
)
