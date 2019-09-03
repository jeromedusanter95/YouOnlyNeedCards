package com.jerome.dusanter.youonlyneedcards.app.game

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.PlayerChooseWinners
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.PotChooseWinners
import com.jerome.dusanter.youonlyneedcards.core.*
import javax.inject.Inject

class GameMapper @Inject internal constructor(
    private val context: Context
) {
    fun mapShowCurrentTurn(
        actionPlayerList: List<ActionPlayer>,
        nameCurrentPlayer: String,
        stackCurrentPlayer: Int,
        namePartTurn: String,
        stackTurn: Int,
        resetTimer: Boolean = false,
        durationBeforeIncreasingBlinds: Long = 0
    ): GameUiModel.ShowCurrentTurn {
        return GameUiModel.ShowCurrentTurn(
            actionPlayerList = actionPlayerList,
            informationsCurrentPlayer = "$nameCurrentPlayer $stackCurrentPlayer",
            namePartTurn = namePartTurn,
            stackTurn = context.getString(R.string.game_activity_number_chips, stackTurn),
            resetTimer = resetTimer,
            durationBeforeIncreasingBlind = durationBeforeIncreasingBlinds
        )
    }

    fun mapToRaiseDialog(bigBlind: Int, stackPlayer: Int): GameUiModel.ShowRaiseDialog {
        return GameUiModel.ShowRaiseDialog(
            bigBlind = bigBlind, stackPlayer = stackPlayer
        )
    }

    fun mapToShowChooseWinnersDialog(potList: List<Pot>): GameUiModel.ShowChooseWinnersDialog {
        val potListChooseWinners = potList.map { pot ->
            PotChooseWinners(pot.potentialWinners.map { player ->
                PlayerChooseWinners(player.id, player.name)
            }, pot.stack)
        }
        return GameUiModel.ShowChooseWinnersDialog(
            potList = potListChooseWinners,
            shouldShowChooseWinnersDialog = potListChooseWinners[0].potentialWinnerList.size > 1
        )
    }

    fun mapToEndTurnDialog(playerEndTurnList: List<PlayerEndTurn>): GameUiModel.ShowEndTurnDialog {
        return GameUiModel.ShowEndTurnDialog(
            playerEndTurnList.map {
                PlayerEndTurnUiModel(
                    when {
                        it.isWinner -> context.getString(
                            R.string.game_activity_end_turn_review_text_winner,
                            it.name,
                            it.stack
                        )
                        it.stack > 0 -> context.getString(
                            R.string.game_activity_end_turn_review_text_loser,
                            it.name,
                            it.stack
                        )
                        else -> context.getString(
                            R.string.game_activity_end_turn_review_text_even,
                            it.name
                        )
                    }
                )
            }
        )
    }

    fun mapEndGameDialog(
        playerEndGameList: List<PlayerEndGame>,
        settings: Settings
    ): GameUiModel.ShowEndGameDialog {
        val listStack = mutableListOf<Int>()
        playerEndGameList.forEach {
            if (!listStack.contains(it.stack)) {
                listStack.add(it.stack)
            }
        }
        listStack.sortWith(Comparator { int1, int2 ->
            int2.compareTo(int1)
        })

        var numberPlayerAlreadyRanked = 0

        listStack.forEach { stack ->
            val playerListForThisStack = playerEndGameList.filter {
                it.stack == stack
            }
            if (playerListForThisStack.size > 1) {
                playerListForThisStack.forEach {
                    it.ranking = getRankingByIndex(
                        numberPlayerAlreadyRanked,
                        true
                    )
                }
                numberPlayerAlreadyRanked += playerListForThisStack.size
            } else {
                playerListForThisStack[0].ranking = getRankingByIndex(
                    listStack.indexOf(stack),
                    false
                )
                numberPlayerAlreadyRanked++
            }
        }

        playerEndGameList.toMutableList().sortWith(Comparator { player1, player2 ->
            player2.stack.compareTo(player1.stack)
        })


        return GameUiModel.ShowEndGameDialog(
            playerEndGameList.map {
                PlayerEndGameUiModel(
                    context.getString(
                        R.string.game_activity_end_game_description,
                        it.name,
                        it.stack
                    ),
                    if (settings.isMoneyBetEnabled && it.isWinner) {
                        context.getString(
                            R.string.game_activity_end_game_description_winner,
                            getAmountMoneyWonOrLost(
                                it.stack,
                                settings.stack,
                                settings.ratioStackMoney
                            )
                        )
                    } else if (settings.isMoneyBetEnabled) {
                        context.getString(
                            R.string.game_activity_end_game_description_loser,
                            getAmountMoneyWonOrLost(
                                it.stack,
                                settings.stack,
                                settings.ratioStackMoney
                            )
                        )
                    } else {
                        context.getString(R.string.game_activity_end_game_description_no_money_bet)
                    },
                    context.getString(
                        R.string.game_activity_end_game_ranking,
                        it.ranking
                    )
                )
            }
        )
    }

    private fun getAmountMoneyWonOrLost(
        currentStack: Int,
        initialStack: Int,
        ratioStackMoney: Int
    ): Int {
        return if ((currentStack - initialStack) / ratioStackMoney > 0) {
            (currentStack - initialStack) / ratioStackMoney
        } else {
            (currentStack - initialStack) / ratioStackMoney * (-1)
        }
    }

    private fun getRankingByIndex(index: Int, isExAquo: Boolean): String {
        val ranking: String = when (index) {
            0 -> context.getString(R.string.game_activity_end_game_ranking_first)
            1 -> context.getString(R.string.game_activity_end_game_ranking_second)
            2 -> context.getString(R.string.game_activity_end_game_ranking_third)
            else -> context.getString(R.string.game_activity_end_game_ranking_others, index + 1)
        }
        if (isExAquo) {
            "$ranking " + context.getString(R.string.game_activity_end_game_ranking_exaquo)
        }
        return ranking
    }

    fun mapToShowCustomStackDialog(
        playerList: List<Player>
    ): GameUiModel.ShowCustomStackDialog {
        return GameUiModel.ShowCustomStackDialog(
            playerList.map {
                PlayerCustomStackUiModel(it.id, it.name, it.stack, CustomStack.Nothing)
            }
        )
    }

    fun mapToCustomStack(playerList: List<PlayerCustomStackUiModel>): List<PlayerCustomStack> {
        return playerList.map {
            PlayerCustomStack(it.id, it.stack, it.action)
        }
    }
}
