package com.jerome.dusanter.youonlyneedcards.app.settings

import com.jerome.dusanter.youonlyneedcards.core.Settings


class SettingsMapper {

    fun mapToUiModelSuccess(settings: Settings): SettingsUiModel.Success? {
        return SettingsUiModel.Success(
            stack = settings.stack.toString()
                + SettingsConstants.CHIPS,
            isIncreaseBlindsEnabled = settings.isIncreaseBlindsEnabled,
            isMoneyBetEnabled = settings.isMoneyBetEnabled,
            smallBlind = settings.smallBlind.toString()
                + "/"
                + settings.smallBlind * 2
                + SettingsConstants.CHIPS,
            frequencyIncreasingBlind = (settings.frequencyIncreasingBlind / 60000).toString()
                + SettingsConstants.MIN,
            money = settings.money.toString() + SettingsConstants.EURO,
            increaseBlindsAnswer = if (settings.isIncreaseBlindsEnabled) {
                SettingsConstants.YES
            } else {
                SettingsConstants.NO
            },
            moneyBetAnswer = if (settings.isMoneyBetEnabled) {
                SettingsConstants.YES
            } else {
                SettingsConstants.NO
            },
            showErrorMoney = settings.isMoneyBetEnabled && settings.money <= 0,
            showErrorFrequencyIncreaseBlinds = settings.isIncreaseBlindsEnabled && settings.frequencyIncreasingBlind <= 0,
            showErrorStack = settings.stack <= 0,
            showErrorBlinds = settings.smallBlind <= 0
        )
    }

    fun mapToUiModelError(settings: Settings): SettingsUiModel.Error {
        return SettingsUiModel.Error(
            showErrorBlinds = settings.smallBlind <= 0,
            showErrorStack = settings.stack <= 0,
            showErrorFrequencyIncreaseBlinds = settings.isIncreaseBlindsEnabled && settings.frequencyIncreasingBlind <= 0,
            showErrorMoney = settings.isMoneyBetEnabled && settings.money <= 0
        )
    }
}
