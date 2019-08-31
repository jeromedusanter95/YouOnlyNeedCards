package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import com.jerome.dusanter.youonlyneedcards.core.Winner
import javax.inject.Inject

class ChooseWinnersMapper @Inject internal constructor() {
    fun mapToWinnersList(pot: PotChooseWinners): MutableList<Winner> {
        return pot.potentialWinnerList.filter { it.isWinner }.map {
            Winner(it.id, pot.stackForEachPlayer)
        }.toMutableList()
    }
}
