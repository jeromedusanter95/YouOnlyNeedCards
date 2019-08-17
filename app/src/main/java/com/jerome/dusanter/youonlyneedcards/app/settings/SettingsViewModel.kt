package com.jerome.dusanter.youonlyneedcards.app.settings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.interactor.SaveSettingsInteractor
import javax.inject.Inject

class SettingsViewModel @Inject internal constructor(
    private val saveSettingsInteractor: SaveSettingsInteractor,
    private val mapper: SettingsMapper
) : ViewModel() {

    val state = MutableLiveData<SettingsUiModel>()
    private var settings: Settings = Settings(
        stack = 0,
        isMoneyBetEnabled = false,
        isIncreaseBlindsEnabled = false,
        money = 0,
        smallBlind = 0,
        frequencyIncreasingBlind = 0,
        ratioStackMoney = 0
    )

    fun onStartGame() {
        val isOneNecessarySettingNull = (settings.smallBlind == 0
                || settings.stack == 0
                || (settings.isMoneyBetEnabled && settings.money == 0)
                || (settings.isIncreaseBlindsEnabled && settings.frequencyIncreasingBlind.toInt() == 0))
        if (isOneNecessarySettingNull) {
            state.value = mapper.mapToUiModelError(settings)
        } else {
            saveSettingsInteractor.execute(settings, buildSaveSettingsListener())
        }

    }

    private fun buildSaveSettingsListener(): SaveSettingsInteractor.Listener =
        object : SaveSettingsInteractor.Listener {
            override fun onSuccess() {
                state.value = SettingsUiModel.GoToGameActivity
            }
        }

    fun start() {
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSeekBarBlindUpdated(progress: Int) {
        settings = settings.copy(smallBlind = progress)
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSeekBarStackUpdated(progress: Int) {
        settings = settings.copy(stack = progress)
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSeekBarFrequencyIncreasedBlindUpdated(progress: Int) {
        val millis = progress * 60 * 1000
        settings = settings.copy(frequencyIncreasingBlind = millis.toLong())
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSeekBarMoneyUpdated(progress: Int) {
        settings = settings.copy(money = progress)
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSwitchMoneyToggled(checked: Boolean) {
        settings = settings.copy(isMoneyBetEnabled = checked)
        state.value = mapper.mapToUiModelSuccess(settings)
    }

    fun onSwitchIncreaseBlindsToggled(checked: Boolean) {
        settings = settings.copy(isIncreaseBlindsEnabled = checked)
        state.value = mapper.mapToUiModelSuccess(settings)
    }
}
