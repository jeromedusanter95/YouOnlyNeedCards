package com.jerome.dusanter.youonlyneedcards.app.game.playerprofile

sealed class PlayerProfileUiModel {
    data class ShowPlayer(
        val name: String,
        val stack: String,
        val stateBlind: String,
        val statePlayer: String,
        val actionPlayer: String
    ) : PlayerProfileUiModel()

    data class ShowRebuy(
        val name: String
    ) : PlayerProfileUiModel()
}
