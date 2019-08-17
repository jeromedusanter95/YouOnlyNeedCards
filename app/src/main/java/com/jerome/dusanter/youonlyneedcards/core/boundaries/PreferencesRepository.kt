package com.jerome.dusanter.youonlyneedcards.core.boundaries

interface PreferencesRepository {
    fun saveGame(gameJson: String)
    fun retrieveGame(): String?
    fun deleteGame()
}
