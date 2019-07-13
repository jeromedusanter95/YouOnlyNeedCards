package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer

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
        val potList: List<PotUiModel>
    ) : GameUiModel()

    data class ShowEndTurn(
        val playerEndTurnList: List<PlayerEndTurnUiModel>
    ) : GameUiModel()

    data class ShowEndGame(
        val playerEndGameList: List<PlayerEndGameUiModel>
    ) : GameUiModel()

    object ShowSaveGame : GameUiModel()
}

data class DialogRaiseUiModel(
    val bigBlind: Int,
    val stackPlayer: Int
)

data class PlayerUiModel(
    val id: String,
    val name: String,
    var isWinner: Boolean = false
)

data class PotUiModel(
    val potentialWinnerList: List<PlayerUiModel>,
    val stack: Int,
    var stackForEachPlayer: Int = 0
)

data class PlayerEndTurnUiModel(
    val description: String
)

data class PlayerEndGameUiModel(
    val description: String,
    val money: String,
    val ranking: String
)
