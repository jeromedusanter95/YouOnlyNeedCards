package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import com.jerome.dusanter.youonlyneedcards.app.game.PlayerUiModel
import com.jerome.dusanter.youonlyneedcards.core.Winner

data class PotUiModel(
    val potentialWinnerList: List<PlayerUiModel>,
    val stack: Int,
    var stackForEachPlayer: Int = 0
)

sealed class ChooseWinnersUiModel {
    data class NextPot(
        val currentPot: PotUiModel,
        val isImageButtonCheck: Boolean
    ) : ChooseWinnersUiModel()

    data class Check(
        val winnerList: MutableList<Winner>
    ) : ChooseWinnersUiModel()

    object Error : ChooseWinnersUiModel()
}
