package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer

class GameMapper {
    fun map(actionPlayerList: List<ActionPlayer>): GameUiModel {
        return GameUiModel(actionPlayerList)
    }
}
