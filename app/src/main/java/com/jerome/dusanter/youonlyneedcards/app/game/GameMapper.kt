package com.jerome.dusanter.youonlyneedcards.app.game

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsConstants
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndGame
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndTurn
import com.jerome.dusanter.youonlyneedcards.core.Pot
import com.jerome.dusanter.youonlyneedcards.core.Settings
import com.jerome.dusanter.youonlyneedcards.core.Winner

class GameMapper {
    fun map(
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
            stackTurn = "$stackTurn ${SettingsConstants.CHIPS}",
            resetTimer = resetTimer,
            durationBeforeIncreasingBlind = durationBeforeIncreasingBlinds
        )
    }

    fun map(bigBlind: Int, stackPlayer: Int): DialogRaiseUiModel {
        return DialogRaiseUiModel(bigBlind = bigBlind, stackPlayer = stackPlayer)
    }

    fun map(potList: List<Pot>): GameUiModel.ShowChooseWinnersDialog {
        return GameUiModel.ShowChooseWinnersDialog(potList.map { pot ->
            PotUiModel(pot.potentialWinners.map { player ->
                PlayerUiModel(player.id, player.name)
            }, pot.stack)
        })
    }

    fun map(playerEndTurnList: List<PlayerEndTurn>): GameUiModel.ShowEndTurn {
        return GameUiModel.ShowEndTurn(
            playerEndTurnList.map {
                PlayerEndTurnUiModel(
                    when {
                        it.isWinner -> "${it.name} a gagné ${it.stack} jetons"
                        it.stack > 0 -> "${it.name} a perdu ${it.stack} jetons"
                        else -> "${it.name} n'a rien perdu"
                    }
                )
            }
        )
    }

    fun map(potUiModel: PotUiModel): List<Winner> {
        return potUiModel.potentialWinnerList.map {
            Winner(it.id, potUiModel.stack)
        }
    }

    //TODO Inject context into constructor
    fun map(
        context: Context,
        playerEndGameList: MutableList<PlayerEndGame>,
        settings: Settings
    ): GameUiModel.ShowEndGame {

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

        playerEndGameList.sortWith(Comparator { player1, player2 ->
            player2.stack.compareTo(player1.stack)
        })


        return GameUiModel.ShowEndGame(
            playerEndGameList.map {
                PlayerEndGameUiModel(
                    context.getString(
                        R.string.poker_activity_end_game_description,
                        it.name,
                        it.stack
                    ),
                    if (settings.isMoneyBetEnabled && it.isWinner) {
                        context.getString(
                            R.string.poker_activity_end_game_description_winner,
                            getAmountMoneyWonOrLost(
                                it.stack,
                                settings.stack,
                                settings.ratioStackMoney
                            )
                        )
                    } else {
                        context.getString(
                            R.string.poker_activity_end_game_description_loser,
                            getAmountMoneyWonOrLost(
                                it.stack,
                                settings.stack,
                                settings.ratioStackMoney
                            )
                        )
                    },
                    context.getString(
                        R.string.poker_activity_end_game_ranking,
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
        var ranking: String = if (index == 0) {
            "1er"
        } else {
            "${index + 1}ème"
        }
        if (isExAquo) {
            ranking += " ex aquo"
        }
        return ranking
    }
}
