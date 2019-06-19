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
        frequencyIncreasingBlind = 0
    )

    fun onStartGame(context: Context) {
        //TODO Inject later
        val interactor = SaveSettingsInteractor()
        interactor.execute(settings, buildSaveSettingsListener(context))
    }

    private fun buildSaveSettingsListener(context: Context): SaveSettingsInteractor.Listener =
        object : SaveSettingsInteractor.Listener {
            override fun onSuccess() {
                context.startActivity(Intent(context, GameActivity::class.java))
            }
        }

    fun start() {
        state.value = mapper.map(settings)
    }

    fun onSeekBarBlindUpdated(progress: Int) {
        settings = settings.copy(smallBlind = progress)
        state.value = mapper.map(settings)
    }

    fun onSeekBarStackUpdated(progress: Int) {
        settings = settings.copy(stack = progress)
        state.value = mapper.map(settings)
    }

    fun onSeekBarFrequencyIncreasedBlindUpdated(progress: Int) {
        settings = settings.copy(frequencyIncreasingBlind = progress)
        state.value = mapper.map(settings)
    }

    fun onSeekBarMoneyUpdated(progress: Int) {
        settings = settings.copy(money = progress)
        state.value = mapper.map(settings)
    }

    fun onSwitchMoneyToggled(checked: Boolean) {
        settings = settings.copy(isMoneyBetEnabled = checked)
        state.value = mapper.map(settings)
    }

    fun onSwitchIncreaseBlindsToggled(checked: Boolean) {
        settings = settings.copy(isIncreaseBlindsEnabled = checked)
        state.value = mapper.map(settings)
    }
}