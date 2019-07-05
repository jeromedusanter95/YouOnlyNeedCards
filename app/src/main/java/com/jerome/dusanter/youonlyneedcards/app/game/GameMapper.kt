package com.jerome.dusanter.youonlyneedcards.app.game

import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsConstants
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.PlayerEndTurn
import com.jerome.dusanter.youonlyneedcards.core.Pot
import com.jerome.dusanter.youonlyneedcards.core.Winner

class GameMapper {
    fun map(
        actionPlayerList: List<ActionPlayer>,
        nameCurrentPlayer: String,
        stackCurrentPlayer: Int,
        namePartTurn: String,
        stackTurn: Int
    ): GameUiModel.ShowCurrentTurn {
        return GameUiModel.ShowCurrentTurn(
            actionPlayerList = actionPlayerList,
            informationsCurrentPlayer = "$nameCurrentPlayer $stackCurrentPlayer",
            namePartTurn = namePartTurn,
            stackTurn = "$stackTurn ${SettingsConstants.CHIPS}"
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
                    if (it.isWinner) {
                        "${it.name} a gagné ${it.stack} jetons"
                    } else {
                        "${it.name} a perdu ${it.stack} jetons"
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
}
