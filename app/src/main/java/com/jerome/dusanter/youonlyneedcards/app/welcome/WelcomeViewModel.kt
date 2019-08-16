package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jerome.dusanter.youonlyneedcards.core.interactor.DeleteGameInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.RetrieveGameInteractor
import javax.inject.Inject

class WelcomeViewModel @Inject internal constructor(
    private val deleteGameInteractor: DeleteGameInteractor,
    private val retrieveGameInteractor: RetrieveGameInteractor
) : ViewModel() {

    val state = MutableLiveData<WelcomeUiModel>()

    fun onNewGame() {
        deleteGameInteractor.execute()
        state.value = WelcomeUiModel.GoToSettingsActivity
    }

    fun onOldGame() {
        retrieveGameInteractor.execute(buildRetrieveGameListener())
    }

    private fun buildRetrieveGameListener(): RetrieveGameInteractor.Listener =
        object : RetrieveGameInteractor.Listener {
            override fun onSuccess() {
                state.value = WelcomeUiModel.GoToGameActivity
            }

            override fun onError() {
                state.value = WelcomeUiModel.Error
            }
        }
}
