package com.jerome.dusanter.youonlyneedcards.data

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
    private const val KEY_GAME = "KEY_GAME"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun saveGame(context: Context, gameJson: String) {
        getSharedPreferences(context).edit().putString(KEY_GAME, gameJson).apply()
    }

    fun retrieveGame(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_GAME, "")
    }

    fun deleteGame(context: Context) {
        getSharedPreferences(context).edit().remove(KEY_GAME).apply()
    }
}
