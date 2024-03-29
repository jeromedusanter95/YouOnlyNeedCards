package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.PotChooseWinners
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.CustomStack

sealed class GameUiModel {
    data class ShowCurrentTurn(
        val actionPlayerList: List<ActionPlayer>,
        val informationsCurrentPlayer: String,
        val namePartTurn: String,
        val stackTurn: String,
        val resetTimer: Boolean,
        val durationBeforeIncreasingBlind: Long
    ) : GameUiModel()

    data class ShowChooseWinnersDialog(
        val potList: List<PotChooseWinners>,
        val shouldShowChooseWinnersDialog: Boolean
    ) : GameUiModel()

    data class ShowEndTurnDialog(
        val playerEndTurnList: List<PlayerEndTurnUiModel>
    ) : GameUiModel()

    data class ShowEndGameDialog(
        val playerEndGameList: List<PlayerEndGameUiModel>
    ) : GameUiModel()

    data class ShowRaiseDialog(
        val bigBlind: Int,
        val stackPlayer: Int
    ) : GameUiModel()

    object ShowErrorNotEnoughtPlayer : GameUiModel()

    data class ShowCustomStackDialog(
        val playerCustomStackList: List<PlayerCustomStackUiModel>
    ) : GameUiModel()
}

data class PlayerEndTurnUiModel(
    val description: String
)

data class PlayerEndGameUiModel(
    val description: String,
    val money: String,
    val ranking: String
)

data class PlayerCustomStackUiModel(
    val id: String,
    val name: String,
    var stack: Int,
    var action: CustomStack
)
