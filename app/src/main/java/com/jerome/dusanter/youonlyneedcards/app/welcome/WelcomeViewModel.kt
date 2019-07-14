package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsActivity
import com.jerome.dusanter.youonlyneedcards.core.interactor.DeleteGameInteractor
import com.jerome.dusanter.youonlyneedcards.core.interactor.RetrieveGameInteractor

class WelcomeViewModel : ViewModel() {

    val state = MutableLiveData<WelcomeUiModel>()

    fun onNewGame(context: Context) {
        DeleteGameInteractor().execute(context)
        val i = Intent(context, SettingsActivity::class.java)
        context.startActivity(i)
    }

    fun onOldGame(context: Context) {
        RetrieveGameInteractor().execute(buildRetrieveGameListener(context), context)
    }

    private fun buildRetrieveGameListener(context: Context): RetrieveGameInteractor.Listener =
        object : RetrieveGameInteractor.Listener {
            override fun onSuccess() {
                val i = Intent(context, GameActivity::class.java)
                i.putExtra("fromWelcome", true)
                context.startActivity(i)
            }

            override fun onError() {
                state.value = WelcomeUiModel.Error
            }
        }
}
