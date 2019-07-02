package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import kotlinx.android.synthetic.main.activity_game.buttonLeft
import kotlinx.android.synthetic.main.activity_game.buttonMiddle
import kotlinx.android.synthetic.main.activity_game.buttonRight
import kotlinx.android.synthetic.main.activity_game.buttonStartGame
import kotlinx.android.synthetic.main.activity_game.buttonStartTurn
import kotlinx.android.synthetic.main.activity_game.playerProfilView1
import kotlinx.android.synthetic.main.activity_game.playerProfilView2
import kotlinx.android.synthetic.main.activity_game.playerProfilView3
import kotlinx.android.synthetic.main.activity_game.playerProfilView4
import kotlinx.android.synthetic.main.activity_game.playerProfilView5
import kotlinx.android.synthetic.main.activity_game.playerProfilView6
import kotlinx.android.synthetic.main.activity_game.playerProfilView7
import kotlinx.android.synthetic.main.activity_game.playerProfilView8
import kotlinx.android.synthetic.main.activity_game.textViewCurrentPlayerInformations
import kotlinx.android.synthetic.main.activity_game.textViewPartTurnName
import kotlinx.android.synthetic.main.activity_game.textViewTurnStack

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        setupListeners()
        setupLiveDatas()
    }

    override fun onStart() {
        super.onStart()
        viewModel.populateGameWithFakeDatas()
    }

    private fun setupLiveDatas() {
        viewModel.statePlayerProfileView1.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView1.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView1.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView2.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView2.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView2.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView3.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView3.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView3.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView4.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView4.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView4.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView5.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView5.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView5.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView6.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView6.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView6.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView7.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView7.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView7.visibility = View.GONE
                }
            })

        viewModel.statePlayerProfileView8.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    playerProfilView8.updateProfilePlayer(uiModel)
                } else {
                    playerProfilView8.visibility = View.GONE
                }
            })

        viewModel.stateGame.observe(
            this,
            Observer { gameUiModel ->
                if (gameUiModel != null) {
                    when (gameUiModel) {
                        is GameUiModel.ShowEndTurn -> updateTableEndTurn(gameUiModel)
                        is GameUiModel.ShowCurrentTurn -> updateTableCurrentTurn(gameUiModel)
                    }
                }
            }
        )

        viewModel.stateDialogRaise.observe(
            this,
            Observer { dialogRaiseUiModel ->
                if (dialogRaiseUiModel != null) {
                    showDialogRaise(dialogRaiseUiModel)
                }
            }
        )
    }

    private fun showDialogRaise(dialogEventUiModel: DialogRaiseUiModel) {
        val dialog = DialogRaise
            .newInstance(dialogEventUiModel)
        dialog.show(fragmentManager, "GameActivity")
    }

    fun onRaise(isAllin: Boolean, stackRaised: Int) {
        if (isAllin) {
            viewModel.play(ActionPlayer.AllIn.name, stackRaised)
        } else {
            viewModel.play(ActionPlayer.Raise.name, stackRaised)
        }
    }


    //TODO use group to handle visibility
    private fun updateTableCurrentTurn(gameUiModel: GameUiModel.ShowCurrentTurn) {
        buttonStartGame.visibility = View.GONE
        buttonStartTurn.visibility = View.GONE
        buttonLeft.visibility = View.VISIBLE
        buttonMiddle.visibility = View.VISIBLE
        buttonRight.visibility = View.VISIBLE
        textViewPartTurnName.visibility = View.VISIBLE
        textViewTurnStack.visibility = View.VISIBLE
        textViewCurrentPlayerInformations.visibility = View.VISIBLE
        buttonLeft.text = gameUiModel.actionPlayerList[0].name
        buttonMiddle.text = gameUiModel.actionPlayerList[1].name
        buttonRight.text = gameUiModel.actionPlayerList[2].name
        textViewCurrentPlayerInformations.text = gameUiModel.informationsCurrentPlayer
        textViewPartTurnName.text = gameUiModel.namePartTurn
        textViewTurnStack.text = gameUiModel.stackTurn
    }

    private fun updateTableEndTurn(gameUiModel: GameUiModel.ShowEndTurn) {
        buttonStartTurn.visibility = View.VISIBLE
        buttonLeft.visibility = View.GONE
        buttonMiddle.visibility = View.GONE
        buttonRight.visibility = View.GONE
        textViewPartTurnName.visibility = View.GONE
        textViewTurnStack.visibility = View.GONE
        textViewCurrentPlayerInformations.visibility = View.GONE
        DialogEndTurn.newInstance(gameUiModel).show(fragmentManager, "DialogEndTurn")
    }

    private fun setupListeners() {
        /*playerProfilView1.imageButtonCheck.setOnClickListener {
            if (!playerProfilView1.getName().isBlank()) {
                viewModel.onAddPlayer("1", playerProfilView1.getName())
                playerProfilView1.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }
        playerProfilView2.imageButtonCheck.setOnClickListener {
            if (!playerProfilView2.getName().isBlank()) {
                viewModel.onAddPlayer("2", playerProfilView2.getName())
                playerProfilView2.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }
        playerProfilView3.imageButtonCheck.setOnClickListener {
            if (!playerProfilView3.getName().isBlank()) {
                viewModel.onAddPlayer("3", playerProfilView3.getName())
                playerProfilView3.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }
        playerProfilView4.imageButtonCheck.setOnClickListener {
            if (!playerProfilView4.getName().isBlank()) {
                viewModel.onAddPlayer("4", playerProfilView4.getName())
                playerProfilView4.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }
        playerProfilView5.imageButtonCheck.setOnClickListener {
            if (!playerProfilView5.getName().isBlank()) {
                viewModel.onAddPlayer("5", playerProfilView5.getName())
                playerProfilView5.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }

        playerProfilView6.imageButtonCheck.setOnClickListener {
            if (!playerProfilView6.getName().isBlank()) {
                viewModel.onAddPlayer("6", playerProfilView6.getName())
                playerProfilView6.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }

        playerProfilView7.imageButtonCheck.setOnClickListener {
            if (!playerProfilView7.getName().isBlank()) {
                viewModel.onAddPlayer("7", playerProfilView7.getName())
                playerProfilView7.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }

        playerProfilView8.imageButtonCheck.setOnClickListener {
            if (!playerProfilView8.getName().isBlank()) {
                viewModel.onAddPlayer("8", playerProfilView8.getName())
                playerProfilView8.hideEditProfileLayoutAndShowPlayerLayout()
            }
        }

        buttonStartGame.setOnClickListener {
            viewModel.onStartGame()
        }*/

        buttonLeft.setOnClickListener {
            viewModel.onClickButtonLeft(buttonLeft.text.toString())
        }

        buttonMiddle.setOnClickListener {
            viewModel.onClickButtonMiddle(buttonMiddle.text.toString())
        }

        buttonRight.setOnClickListener {
            viewModel.onClickButtonRight(buttonRight.text.toString())
        }

        buttonStartTurn.setOnClickListener {
            viewModel.onStartTurn()
        }
    }

    override fun onBackPressed() {
        viewModel.populateGameWithFakeDatas()
    }
}
