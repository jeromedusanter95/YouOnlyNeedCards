package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import com.jerome.dusanter.youonlyneedcards.core.Winner

data class PotChooseWinners(
    val potentialWinnerList: List<PlayerChooseWinners>,
    val stack: Int,
    var stackForEachPlayer: Int = 0
)

data class PlayerChooseWinners(
    val id: String,
    val name: String,
    var isWinner: Boolean = false
)

sealed class ChooseWinnersUiModel {
    data class NextPot(
        val currentPot: PotChooseWinners,
        val isImageButtonCheck: Boolean
    ) : ChooseWinnersUiModel()

    data class Check(
        val winnerList: MutableList<Winner>
    ) : ChooseWinnersUiModel()

    object Error : ChooseWinnersUiModel()

    data class RefreshList(
        val potChooseWinners: PotChooseWinners
    ) : ChooseWinnersUiModel()
}
