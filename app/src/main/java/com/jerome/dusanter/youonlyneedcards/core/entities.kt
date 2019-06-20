package com.jerome.dusanter.youonlyneedcards.core

data class Player(
    val id: String,
    var name: String,
    var stack: Int,
    var stackBetTurn: Int,
    var stackBetPartTurn: Int,
    var statePlayer: StatePlayer,
    var stateBlind: StateBlind
)

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
    Nothing,
    CurrentTurn,
    Check,
    Call,
    Raise,
    Fold,
    AllIn,
    Eliminate
}

enum class ActionPlayer {
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