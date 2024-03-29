package com.jerome.dusanter.youonlyneedcards.core

import java.io.Serializable

data class Player(
    val id: String,
    var name: String,
    var stack: Int,
    var stackBetTurn: Int = 0,
    var stackBetPartTurn: Int = 0,
    var statePlayer: StatePlayer = StatePlayer.Playing,
    var stateBlind: StateBlind = StateBlind.Nothing,
    var actionPlayer: ActionPlayer = ActionPlayer.Nothing
) : Serializable


data class PlayerEndTurn(
    val id: String,
    val name: String,
    val stack: Int,
    val isWinner: Boolean
)

data class PlayerEndGame(
    val id: String,
    val name: String,
    val stack: Int,
    val isWinner: Boolean,
    var ranking: String = ""
)

data class PlayerCustomStack(
    val id: String,
    val stack: Int,
    val customStack: CustomStack
)

enum class CustomStack {
    Add, Withdraw, Nothing
}

data class Winner(
    val id: String,
    val stackWon: Int
)

data class Pot(
    val potentialWinners: List<Player>,
    val stack: Int
) : Serializable

data class Settings(
    val stack: Int,
    val isMoneyBetEnabled: Boolean,
    val money: Int,
    var smallBlind: Int,
    val isIncreaseBlindsEnabled: Boolean,
    val frequencyIncreasingBlind: Long,
    var ratioStackMoney: Int
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

data class SavedParams(
    var listPlayers: ArrayList<Player>,
    var settings: Settings,
    var timeRemainingBeforeIncreasingBlinds: Long
)
