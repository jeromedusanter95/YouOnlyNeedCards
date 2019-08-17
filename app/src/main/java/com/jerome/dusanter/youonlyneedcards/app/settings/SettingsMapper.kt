package com.jerome.dusanter.youonlyneedcards.app.settings

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.core.Settings
import javax.inject.Inject


class SettingsMapper @Inject internal constructor(
    private val context: Context
) {
    fun mapToUiModelSuccess(settings: Settings): SettingsUiModel.Success? {
        return SettingsUiModel.Success(
            stack = "${settings.stack} " + context.resources.getString(R.string.common_chips),
            isIncreaseBlindsEnabled = settings.isIncreaseBlindsEnabled,
            isMoneyBetEnabled = settings.isMoneyBetEnabled,
            smallBlind = "${settings.smallBlind}/${settings.smallBlind * 2} "
                    + context.resources.getString(R.string.common_chips),
            frequencyIncreasingBlind = "${(settings.frequencyIncreasingBlind / 60000)} "
                    + context.resources.getString(R.string.common_min),
            money = "${settings.money} " + context.getString(R.string.common_euros),
            increaseBlindsAnswer = if (settings.isIncreaseBlindsEnabled) {
                context.resources.getString(R.string.common_yes)
            } else {
                context.resources.getString(R.string.common_no)
            },
            moneyBetAnswer = if (settings.isMoneyBetEnabled) {
                context.resources.getString(R.string.common_yes)
            } else {
                context.resources.getString(R.string.common_no)
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
