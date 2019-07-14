package com.jerome.dusanter.youonlyneedcards.app.settings

sealed class SettingsUiModel {
    data class Success(
        val stack: String,
        val isMoneyBetEnabled: Boolean,
        val money: String,
        val smallBlind: String,
        val isIncreaseBlindsEnabled: Boolean,
        val frequencyIncreasingBlind: String,
        val moneyBetAnswer: String,
        val increaseBlindsAnswer: String,
        val showErrorBlinds: Boolean,
        val showErrorStack: Boolean,
        val showErrorMoney: Boolean,
        val showErrorFrequencyIncreaseBlinds: Boolean
    ) : SettingsUiModel()

    data class Error(
        val showErrorBlinds: Boolean,
        val showErrorStack: Boolean,
        val showErrorMoney: Boolean,
        val showErrorFrequencyIncreaseBlinds: Boolean
    ) : SettingsUiModel()
}

