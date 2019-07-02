package com.jerome.dusanter.youonlyneedcards.core

data class Player(
    val id: String,
    var name: String,
    var stack: Int,
    var stackBetTurn: Int = 0,
    var stackBetPartTurn: Int = 0,
    var statePlayer: StatePlayer = StatePlayer.Playing,
    var stateBlind: StateBlind = StateBlind.Nothing,
    var actionPlayer: ActionPlayer = ActionPlayer.Nothing
)

data class Winner(val name: String, val stackWon: Int)

data class Settings(
    val stack: Int,
    val isMoneyBetEnabled: Boolean,
    val money: Int,
    val smallBlind: Int,
    val isIncreaseBlindsEnabled: Boolean,
    val frequencyIncreasingBlind: Int
)

enum class StateBlind {
    Nothing,
    Dealer,
    SmallBlind,
    BigBlind
}

enum class StatePlayer {
    Playing,
    CurrentTurn,
    Eliminate
}

enum class ActionPlayer {
    Nothing,
    Check,
    Call,
    Raise,
    Fold,
    AllIn
}

enum class StateTurn {
    PreFlop,
    Flop,
    Turn,
    River
}
