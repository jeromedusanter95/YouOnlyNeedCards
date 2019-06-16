package com.jerome.dusanter.youonlyneedcards.app.settings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

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

    fun onStartGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun start(){
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