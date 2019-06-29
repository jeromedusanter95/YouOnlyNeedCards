package com.jerome.dusanter.youonlyneedcards.core

data class Player(
    val id: String,
    var name: String,
    var stack: Int,
    var stackBetTurn: Int,
    var stackBetPartTurn: Int,
    var statePlayer: StatePlayer,
    var stateBlind: StateBlind,
    var actionPlayer: ActionPlayer

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
