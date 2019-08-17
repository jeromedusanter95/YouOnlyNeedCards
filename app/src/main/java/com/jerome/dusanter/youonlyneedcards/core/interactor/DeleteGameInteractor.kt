package com.jerome.dusanter.youonlyneedcards.core.interactor

import com.jerome.dusanter.youonlyneedcards.core.boundaries.PreferencesRepository
import com.jerome.dusanter.youonlyneedcards.data.GameRepositoryImpl
import javax.inject.Inject

class DeleteGameInteractor @Inject internal constructor(
    private val preferencesRepository: PreferencesRepository
) {
    fun execute() {
        GameRepositoryImpl.resetGame()
        preferencesRepository.deleteGame()
    }
}
