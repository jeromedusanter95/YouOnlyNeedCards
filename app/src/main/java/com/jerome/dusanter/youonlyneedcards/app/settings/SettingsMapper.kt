package com.jerome.dusanter.youonlyneedcards.app.settings

import com.jerome.dusanter.youonlyneedcards.core.Settings


class SettingsMapper {

    fun map(settings: Settings): SettingsUiModel? {
        return SettingsUiModel(
            stack = (Math.round((settings.stack / 10).toFloat()) * 10).toString()
                + SettingsConstants.CHIPS,
            isIncreaseBlindsEnabled = settings.isIncreaseBlindsEnabled,
            isMoneyBetEnabled = settings.isMoneyBetEnabled,
            smallBlind = (Math.round((settings.smallBlind / 10).toFloat()) * 10).toString()
                + "/"
                + Math.round((settings.smallBlind / 10).toFloat()) * 10 * 2
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