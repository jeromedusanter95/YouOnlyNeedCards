package com.jerome.dusanter.youonlyneedcards.app.settings

data class SettingsUiModel(
    val stack: String,
    val isMoneyBetEnabled: Boolean,
    val money: String,
    val smallBlind: String,
    val isIncreaseBlindsEnabled: Boolean,
    val frequencyIncreasingBlind: String,
    val moneyBetAnswer: String,
    val increaseBlindsAnswer: String
)
