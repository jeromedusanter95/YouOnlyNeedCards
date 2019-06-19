package com.jerome.dusanter.youonlyneedcards.core

data class Player(
    val id: String,
    val name: String,
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

enum class StateBlind(val title: String) {
    Nothing(title = "---"),
    Dealer(title = "Dealer"),
    SmallBlind(title = "Small blind"),
    BigBlind(title = "Big blind")
}

enum class StatePlayer(val title: String) {
    Nothing(title = "---"),
    Playing(title = "Playing"),
    Check(title = "Check"),
    Call(title = "Call"),
    Raise(title = "Raise"),
    Fold(title = "Fold"),
    AllIn(title = "AllIn"),
    Eliminate(title = "Eliminate")
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

enum class Parameter {
    Stack,
    IsMoneyBetEnabled,
    Money,
    IsIncreaseBlindsEnabled,
    FrequencyIncreasingBlind,
    IncreaseBlinds
}