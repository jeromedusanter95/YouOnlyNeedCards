package com.jerome.dusanter.youonlyneedcards.app.settings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.interactor.SaveSettingsInteractor

class SettingsViewModel : ViewModel() {

    val state = MutableLiveData<SettingsUiModel>()
    //TODO Inject properly later
    private val mapper = SettingsMapper()
    private var settings: Settings = Settings(
        stack = 0,
        isMoneyBetEnabled = false,
        isIncreaseBlindsEnabled = false,
        money = 0,
        smallBlind = 0,
        frequencyIncreasingBlind = 0,
        ratioStackMoney = 0
    )

    fun onStartGame(context: Context) {
        //TODO Inject later interactor & context
        if (settings.smallBlind == 0
            || settings.stack == 0
            || (settings.isMoneyBetEnabled && settings.money == 0)
            || (settings.isIncreaseBlindsEnabled && settings.frequencyIncreasingBlind.toInt() == 0)
        ) {
            state.value = SettingsMapper().mapToUiModelError(settings)
        } else {
            SaveSettingsInteractor().execute(settings, buildSaveSettingsListener(context))
        }

    }

    private fun buildSaveSettingsListener(context: Context): SaveSettingsInteractor.Listener =
        object : SaveSettingsInteractor.Listener {
            override fun onSuccess() {
                context.startActivity(Intent(context, GameActivity::class.java))
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
        val milis = progress * 60 * 1000
        settings = settings.copy(frequencyIncreasingBlind = milis.toLong())
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
