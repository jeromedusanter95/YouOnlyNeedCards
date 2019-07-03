package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsConstants
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Pot

class GameMapper {
    fun map(
        actionPlayerList: List<ActionPlayer>,
        nameCurrentPlayer: String,
        stackCurrentPlayer: Int,
        namePartTurn: String,
        stackTurn: Int
    ): GameUiModel.ShowCurrentTurn {
        return GameUiModel.ShowCurrentTurn(
            actionPlayerList = actionPlayerList,
            informationsCurrentPlayer = "$nameCurrentPlayer $stackCurrentPlayer",
            namePartTurn = namePartTurn,
            stackTurn = "$stackTurn ${SettingsConstants.CHIPS}"
        )
    }


    fun map(bigBlind: Int, stackPlayer: Int): DialogRaiseUiModel {
        return DialogRaiseUiModel(bigBlind = bigBlind, stackPlayer = stackPlayer)
    }

    fun map(potList: List<Pot>): GameUiModel.ShowEndTurn {
        return GameUiModel.ShowEndTurn(potList)
    }
}
