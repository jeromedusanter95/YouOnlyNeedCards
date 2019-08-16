package com.jerome.dusanter.youonlyneedcards.app.welcome

sealed class WelcomeUiModel {
    object Error : WelcomeUiModel()
    object GoToSettingsActivity : WelcomeUiModel()
    object GoToGameActivity : WelcomeUiModel()}
