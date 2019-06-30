package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsConstants
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer

class GameMapper {
    fun map(
        actionPlayerList: List<ActionPlayer>,
        nameCurrentPlayer: String,
        stackCurrentPlayer: Int,
        namePartTurn: String,
        stackTurn: Int
    ): GameUiModel {
        return GameUiModel(
            actionPlayerList = actionPlayerList,
            informationsCurrentPlayer = "$nameCurrentPlayer $stackCurrentPlayer",
            namePartTurn = namePartTurn,
            stackTurn = "$stackTurn ${SettingsConstants.CHIPS}"
        )
    }


    fun map(bigBlind: Int, stackPlayer: Int): DialogRaiseUiModel {
        return DialogRaiseUiModel(bigBlind = bigBlind, stackPlayer = stackPlayer)
    }
}
