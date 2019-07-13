package com.jerome.dusanter.youonlyneedcards.core.interactor

class SaveGameInSharedPreferencesInteractor {

    fun execute(listener: Listener) {
        listener.onSuccess()
    }

    interface Listener {
        fun onSuccess()
    }
}
