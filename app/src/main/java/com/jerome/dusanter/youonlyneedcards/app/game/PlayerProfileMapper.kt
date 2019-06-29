package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsConstants
import com.jerome.dusanter.youonlyneedcards.core.Player

class PlayerProfileMapper {
    fun map(player: Player): PlayerProfileUiModel {
        return PlayerProfileUiModel(
            name = player.name,
            stack = player.stack.toString() + SettingsConstants.CHIPS,
            stateBlind = player.stateBlind.name,
            statePlayer = player.statePlayer.name,
            actionPlayer = player.actionPlayer.name
        )
    }
}
