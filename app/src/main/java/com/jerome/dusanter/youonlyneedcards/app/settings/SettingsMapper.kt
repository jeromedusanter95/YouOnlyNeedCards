package com.jerome.dusanter.youonlyneedcards.app.settings

import com.jerome.dusanter.youonlyneedcards.core.Settings


class SettingsMapper {

    fun map(settings: Settings): SettingsUiModel? {
        return SettingsUiModel(
            stack = settings.stack.toString()
                + SettingsConstants.CHIPS,
            isIncreaseBlindsEnabled = settings.isIncreaseBlindsEnabled,
            isMoneyBetEnabled = settings.isMoneyBetEnabled,
            smallBlind = settings.smallBlind.toString()
                + "/"
                + settings.smallBlind * 2
                + SettingsConstants.CHIPS,
            frequencyIncreasingBlind = settings.frequencyIncreasingBlind.toString()
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
            }
        )
    }
}
