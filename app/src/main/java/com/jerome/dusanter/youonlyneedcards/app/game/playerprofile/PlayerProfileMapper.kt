package com.jerome.dusanter.youonlyneedcards.app.game.playerprofile

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.core.Player
import javax.inject.Inject

class PlayerProfileMapper @Inject internal constructor(
    private val context: Context
) {
    fun mapToProfilePlayerUiModel(player: Player): PlayerProfileUiModel.ShowPlayer {
        return PlayerProfileUiModel.ShowPlayer(
            name = player.name,
            stack = context.getString(R.string.game_activity_number_chips, player.stack),
            stateBlind = player.stateBlind.name,
            statePlayer = player.statePlayer.name,
            actionPlayer = player.actionPlayer.name
        )
    }
}
