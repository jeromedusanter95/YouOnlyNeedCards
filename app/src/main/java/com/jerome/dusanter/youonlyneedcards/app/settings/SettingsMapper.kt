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
            stack = mapToStack(settings),
            isIncreaseBlindsEnabled = settings.isIncreaseBlindsEnabled,
            isMoneyBetEnabled = settings.isMoneyBetEnabled,
            smallBlind = mapToSmallBlind(settings),
            frequencyIncreasingBlind = mapToFrequencyIncreaseBlinds(settings),
            money = mapToMoney(settings),
            increaseBlindsAnswer = mapToIncreaseBlindsAnswer(settings),
            moneyBetAnswer = mapToMoneyAnswer(settings),
            showErrorMoney = showErrorMoney(settings),
            showErrorFrequencyIncreaseBlinds = showErrorFrequencyIncreaseBlinds(settings),
            showErrorStack = settings.stack <= 0,
            showErrorBlinds = settings.smallBlind <= 0
        )
    }

    fun mapToUiModelError(settings: Settings): SettingsUiModel.Error {
        return SettingsUiModel.Error(
            showErrorMoney = showErrorMoney(settings),
            showErrorFrequencyIncreaseBlinds = showErrorFrequencyIncreaseBlinds(settings),
            showErrorStack = settings.stack <= 0,
            showErrorBlinds = settings.smallBlind <= 0
        )
    }

    private fun mapToStack(settings: Settings) =
        "${settings.stack} " + context.resources.getString(R.string.common_chips)

    private fun mapToSmallBlind(settings: Settings) =
        "${settings.smallBlind}/${settings.smallBlind * 2} " + context.resources.getString(R.string.common_chips)

    private fun mapToFrequencyIncreaseBlinds(settings: Settings) =
        ("${(settings.frequencyIncreasingBlind / 60000)} "
                + context.resources.getString(R.string.common_min))

    private fun mapToMoney(settings: Settings) =
        "${settings.money} " + context.getString(R.string.common_euros)

    private fun mapToIncreaseBlindsAnswer(settings: Settings): String {
        return if (settings.isIncreaseBlindsEnabled) {
            context.resources.getString(R.string.common_yes)
        } else {
            context.resources.getString(R.string.common_no)
        }
    }

    private fun mapToMoneyAnswer(settings: Settings): String {
        return if (settings.isMoneyBetEnabled) {
            context.resources.getString(R.string.common_yes)
        } else {
            context.resources.getString(R.string.common_no)
        }
    }

    private fun showErrorMoney(settings: Settings) =
        settings.isMoneyBetEnabled && settings.money <= 0

    private fun showErrorFrequencyIncreaseBlinds(settings: Settings) =
        settings.isIncreaseBlindsEnabled && settings.frequencyIncreasingBlind <= 0

}
