package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import com.jerome.dusanter.youonlyneedcards.core.Winner
import javax.inject.Inject

class ChooseWinnersMapper @Inject internal constructor() {
    fun map(pot: PotUiModel): MutableList<Winner> {
        return pot.potentialWinnerList.filter { it.isWinner }.map {
            Winner(it.id, pot.stackForEachPlayer)
        }.toMutableList()
    }
}
