package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity

class WelcomeViewModel : ViewModel() {

    fun onNewGame(context: Context) {
        //val i = Intent(context, SettingsActivity::class.java)
        //context.startActivity(i)
        val i = Intent(context, GameActivity::class.java)
        context.startActivity(i)
    }

    fun onOldGame() {
        TODO("not implemented") //getCurrentGame
    }
}
