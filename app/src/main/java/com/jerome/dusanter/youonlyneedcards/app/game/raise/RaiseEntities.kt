package com.jerome.dusanter.youonlyneedcards.app.game.raise

sealed class RaiseUiModel {
    data class Update(val stackRaised: String) : RaiseUiModel()
    data class Check(val stackRaised: Int, val isAllin: Boolean) : RaiseUiModel()
}

data class Raise(
    val bigBlind: Int,
    val stackPlayer: Int,
    val stackRaised: Int,
    val isAllin: Boolean
)
