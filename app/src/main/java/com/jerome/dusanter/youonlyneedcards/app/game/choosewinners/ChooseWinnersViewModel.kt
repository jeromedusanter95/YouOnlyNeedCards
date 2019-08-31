package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jerome.dusanter.youonlyneedcards.core.Winner
import javax.inject.Inject

class ChooseWinnersViewModel @Inject internal constructor(
    private val mapper: ChooseWinnersMapper
) : ViewModel() {

    val state = MutableLiveData<ChooseWinnersUiModel>()
    private var potList = mutableListOf<PotChooseWinners>()
    private var winnerList = mutableListOf<Winner>()
    private var currentIndex = 0

    fun onStartDialog(potList: List<PotChooseWinners>) {
        this.potList = potList.toMutableList()
        updateView(potList)
    }


    fun onChoosePlayer(pot: PotChooseWinners) {
        state.value = ChooseWinnersUiModel.RefreshList(pot)
    }

    fun onClickImageButton() {
        if (currentIndex == potList.lastIndex) {
            onCheck()
        } else {
            onNextPot()
        }
    }

    private fun onCheck() {
        winnerList.addAll(mapper.mapToWinnersList(potList[currentIndex]))
        state.value = ChooseWinnersUiModel.Check(winnerList)
    }

    private fun onNextPot() {
        winnerList.addAll(mapper.mapToWinnersList(potList[currentIndex]))
        currentIndex++
        updateView(potList)
    }

    private fun updateView(potList: List<PotChooseWinners>) {
        state.value = ChooseWinnersUiModel.NextPot(
            currentPot = potList[currentIndex],
            isImageButtonCheck = currentIndex == potList.lastIndex
        )
    }
}
