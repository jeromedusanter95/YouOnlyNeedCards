package com.jerome.dusanter.youonlyneedcards.app.game.raise

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class RaiseViewModel @Inject internal constructor(
    private val mapper: RaiseMapper
) : ViewModel() {

    val state = MutableLiveData<RaiseUiModel>()
    var raise: Raise = Raise(
        stackRaised = 0,
        stackPlayer = 0,
        isAllin = false,
        bigBlind = 0
    )

    fun onStart(bigBlind: Int, stackPlayer: Int) {
        raise = raise.copy(bigBlind = bigBlind, stackPlayer = stackPlayer)
    }

    fun onSeekBarRaiseChanged(progress: Int) {
        raise = raise.copy(stackRaised = progress, isAllin = progress == raise.stackPlayer)
        state.value = mapper.mapToUpdate(raise)
    }

    fun onAllIn() {
        raise = raise.copy(stackRaised = raise.stackPlayer, isAllin = true)
        state.value = mapper.mapToUpdate(raise)
    }

    fun onMinRaise() {
        raise = raise.copy(stackRaised = raise.bigBlind, isAllin = false)
        state.value = mapper.mapToUpdate(raise)
    }

    fun onCheck() {
        state.value = mapper.mapToCheck(raise)
    }

}
