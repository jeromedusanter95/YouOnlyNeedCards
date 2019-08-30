package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class ChooseWinnersViewModel @Inject internal constructor(
    private val mapper: ChooseWinnersMapper
) : ViewModel() {

    val state = MutableLiveData<ChooseWinnersUiModel>()

    private var potList = mutableListOf<PotUiModel>()

    fun onStart(potList: MutableList<PotUiModel>) {
        this.potList = potList
    }

    fun onChoosePlayer(pot: PotUiModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onClickImageButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onNextPot() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onCheck() {
        
    }


}
