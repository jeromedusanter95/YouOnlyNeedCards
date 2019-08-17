package com.jerome.dusanter.youonlyneedcards.data

import android.content.SharedPreferences
import com.jerome.dusanter.youonlyneedcards.core.boundaries.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject internal constructor(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {

    override fun saveGame(gameJson: String) {
        sharedPreferences.edit().putString(KEY_GAME, gameJson).apply()
    }

    override fun retrieveGame(): String? {
        return sharedPreferences.getString(KEY_GAME, "")
    }

    override fun deleteGame() {
        sharedPreferences.edit().remove(KEY_GAME).apply()
    }

    companion object {
        private const val KEY_GAME = "KEY_GAME"
    }
}
