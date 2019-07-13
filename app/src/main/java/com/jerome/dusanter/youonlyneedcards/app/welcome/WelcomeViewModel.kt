package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.core.interactor.RetrieveGameFromSharedPreferencesInteractor

class WelcomeViewModel : ViewModel() {

    val state = MutableLiveData<WelcomeUiModel>()

    fun onNewGame(context: Context) {
        //val i = Intent(context, SettingsActivity::class.java)
        //context.startActivity(i)
        val i = Intent(context, GameActivity::class.java)
        context.startActivity(i)
    }

    fun onOldGame(context: Context) {
        RetrieveGameFromSharedPreferencesInteractor().execute(
            buildRetrieveGameFromSharedPreferencesListener(context),
            context
        )
    }

    private fun buildRetrieveGameFromSharedPreferencesListener(context: Context): RetrieveGameFromSharedPreferencesInteractor.Listener =
        object : RetrieveGameFromSharedPreferencesInteractor.Listener {
            override fun onSuccess() {
                val i = Intent(context, GameActivity::class.java)
                context.startActivity(i)
            }

            override fun onError() {
                state.value = WelcomeUiModel.Error
            }
        }
}
