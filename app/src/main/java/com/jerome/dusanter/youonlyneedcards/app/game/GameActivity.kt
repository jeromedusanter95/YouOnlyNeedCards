package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.ChooseWinnersDialog
import com.jerome.dusanter.youonlyneedcards.app.game.customstack.CustomStackDialog
import com.jerome.dusanter.youonlyneedcards.app.game.endgame.ConfirmationEndGameDialog
import com.jerome.dusanter.youonlyneedcards.app.game.endgame.EndGameDialog
import com.jerome.dusanter.youonlyneedcards.app.game.endturn.EndTurnDialog
import com.jerome.dusanter.youonlyneedcards.app.game.raise.RaiseDialog
import com.jerome.dusanter.youonlyneedcards.app.welcome.WelcomeActivity
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Winner
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.buttonCustomStack
import kotlinx.android.synthetic.main.activity_game.buttonEndGame
import kotlinx.android.synthetic.main.activity_game.buttonLeft
import kotlinx.android.synthetic.main.activity_game.buttonMiddle
import kotlinx.android.synthetic.main.activity_game.buttonRight
import kotlinx.android.synthetic.main.activity_game.buttonStartGame
import kotlinx.android.synthetic.main.activity_game.buttonStartTurn
import kotlinx.android.synthetic.main.activity_game.groupBottomButtonsBetweenTurn
import kotlinx.android.synthetic.main.activity_game.groupBottomButtonsDuringTurn
import kotlinx.android.synthetic.main.activity_game.groupTextViewsDuringTurn
import kotlinx.android.synthetic.main.activity_game.playerProfilView1
import kotlinx.android.synthetic.main.activity_game.playerProfilView2
import kotlinx.android.synthetic.main.activity_game.playerProfilView3
import kotlinx.android.synthetic.main.activity_game.playerProfilView4
import kotlinx.android.synthetic.main.activity_game.playerProfilView5
import kotlinx.android.synthetic.main.activity_game.playerProfilView6
import kotlinx.android.synthetic.main.activity_game.playerProfilView7
import kotlinx.android.synthetic.main.activity_game.playerProfilView8
import kotlinx.android.synthetic.main.activity_game.textViewCurrentPlayerInformations
import kotlinx.android.synthetic.main.activity_game.textViewErrorNotEnoughtPlayer
import kotlinx.android.synthetic.main.activity_game.textViewPartTurnName
import kotlinx.android.synthetic.main.activity_game.textViewTimerIncreaseBlinds
import kotlinx.android.synthetic.main.activity_game.textViewTimerIncreaseBlindsTitle
import kotlinx.android.synthetic.main.activity_game.textViewTurnStack
import kotlinx.android.synthetic.main.layout_profil_player_view.view.constraintLayoutRebuyPlayer
import kotlinx.android.synthetic.main.layout_profil_player_view.view.imageButtonCheck
import javax.inject.Inject


class GameActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GameViewModel
    private var timeRemainingBeforeIncreaseBlinds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        setupListeners()
        setupLiveDatas()
        if (intent.getBooleanExtra("fromWelcome", false)) {
            viewModel.onStartTurn()
        }
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
                        is GameUiModel.ShowChooseWinnersDialog -> updateTableChooseWinners(
                            gameUiModel
                        )
                        is GameUiModel.ShowCurrentTurn -> updateTableCurrentTurn(gameUiModel)
                        is GameUiModel.ShowEndTurnDialog -> showDialogEndTurn(gameUiModel)
                        is GameUiModel.ShowEndGameDialog -> showDialogEndGame(gameUiModel)
                        is GameUiModel.ShowErrorNotEnoughtPlayer -> showErrorNotEnoughPlayer()
                        is GameUiModel.ShowRaiseDialog -> showDialogRaise(
                            gameUiModel.raiseDialogUiModel
                        )
                        is GameUiModel.ShowCustomStackDialog -> showDialogCustomStack(gameUiModel)
                    }
                }
            }
        )
    }

    private fun showErrorNotEnoughPlayer() {
        textViewErrorNotEnoughtPlayer.visibility = View.VISIBLE
    }

    private fun showDialogEndGame(gameDialogUiModel: GameUiModel.ShowEndGameDialog) {
        EndGameDialog.newInstance(gameDialogUiModel).show(supportFragmentManager, "EndGameDialog")
    }

    private fun showDialogEndTurn(gameUiModel: GameUiModel.ShowEndTurnDialog) {
        EndTurnDialog.newInstance(gameUiModel).show(supportFragmentManager, "EndTurnDialog")
        viewModel.saveGame(timeRemainingBeforeIncreaseBlinds.toLong())
    }

    private fun showDialogRaise(eventDialogUiModel: RaiseDialogUiModel) {
        RaiseDialog.newInstance(eventDialogUiModel).show(supportFragmentManager, "GameActivity")
    }

    fun onDismissRaiseDialog(isAllin: Boolean, stackRaised: Int) {
        if (isAllin) {
            viewModel.play(ActionPlayer.AllIn.name, stackRaised)
        } else {
            viewModel.play(ActionPlayer.Raise.name, stackRaised)
        }
    }

    private fun updateTableCurrentTurn(gameUiModel: GameUiModel.ShowCurrentTurn) {
        buttonStartGame.visibility = View.GONE
        groupBottomButtonsBetweenTurn.visibility = View.GONE
        groupBottomButtonsDuringTurn.visibility = View.VISIBLE
        groupTextViewsDuringTurn.visibility = View.VISIBLE
        buttonLeft.text = gameUiModel.actionPlayerList[0].name
        buttonMiddle.text = gameUiModel.actionPlayerList[1].name
        if (gameUiModel.actionPlayerList.size > 2) {
            buttonRight.text = gameUiModel.actionPlayerList[2].name
            buttonRight.visibility = View.VISIBLE
        } else {
            buttonRight.visibility = View.GONE
        }
        textViewCurrentPlayerInformations.text = gameUiModel.informationsCurrentPlayer
        textViewPartTurnName.text = gameUiModel.namePartTurn
        textViewTurnStack.text = gameUiModel.stackTurn
        if (gameUiModel.resetTimer) {
            startTimer(gameUiModel.durationBeforeIncreasingBlind)
            textViewTimerIncreaseBlindsTitle.visibility = View.VISIBLE
        }
    }

    private fun startTimer(durationInMillis: Long) {
        object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingBeforeIncreaseBlinds = millisUntilFinished.toInt()
                val second = millisUntilFinished / 1000 % 60
                val minute = millisUntilFinished / (1000 * 60) % 60
                val hour = millisUntilFinished / (1000 * 60 * 60) % 24
                textViewTimerIncreaseBlinds.text = String.format(
                    "%02d:%02d:%02d",
                    hour,
                    minute,
                    second
                )
            }

            override fun onFinish() {
                viewModel.increaseBlinds()
            }
        }.start()
    }

    private fun updateTableChooseWinners(gameUiModel: GameUiModel.ShowChooseWinnersDialog) {
        if (gameUiModel.potList[0].potentialWinnerList.size > 1) {
            ChooseWinnersDialog.newInstance(gameUiModel)
                .show(supportFragmentManager, "ChooseWinnersDialog")
        } else {
            viewModel.onDistributeStack(gameUiModel.potList[0])
        }
        groupBottomButtonsBetweenTurn.visibility = View.VISIBLE
        groupBottomButtonsDuringTurn.visibility = View.GONE
        buttonRight.visibility = View.GONE
        groupTextViewsDuringTurn.visibility = View.GONE
    }

    fun onDismissChooseWinnersDialog(winnerList: List<Winner>) {
        viewModel.onDistributeStack(winnerList)
    }

    fun onDismissEndTurnDialog() {
        viewModel.onCheckIfGameOver()
    }

    private fun setupListeners() {
        playerProfilView1.imageButtonCheck.setOnClickListener {
            if (!playerProfilView1.getName().isBlank()) {
                viewModel.onAddPlayer("1", playerProfilView1.getName())
            }
        }
        playerProfilView2.imageButtonCheck.setOnClickListener {
            if (!playerProfilView2.getName().isBlank()) {
                viewModel.onAddPlayer("2", playerProfilView2.getName())
            }
        }
        playerProfilView3.imageButtonCheck.setOnClickListener {
            if (!playerProfilView3.getName().isBlank()) {
                viewModel.onAddPlayer("3", playerProfilView3.getName())
            }
        }
        playerProfilView4.imageButtonCheck.setOnClickListener {
            if (!playerProfilView4.getName().isBlank()) {
                viewModel.onAddPlayer("4", playerProfilView4.getName())
            }
        }
        playerProfilView5.imageButtonCheck.setOnClickListener {
            if (!playerProfilView5.getName().isBlank()) {
                viewModel.onAddPlayer("5", playerProfilView5.getName())
            }
        }

        playerProfilView6.imageButtonCheck.setOnClickListener {
            if (!playerProfilView6.getName().isBlank()) {
                viewModel.onAddPlayer("6", playerProfilView6.getName())
            }
        }

        playerProfilView7.imageButtonCheck.setOnClickListener {
            if (!playerProfilView7.getName().isBlank()) {
                viewModel.onAddPlayer("7", playerProfilView7.getName())
            }
        }

        playerProfilView8.imageButtonCheck.setOnClickListener {
            if (!playerProfilView8.getName().isBlank()) {
                viewModel.onAddPlayer("8", playerProfilView8.getName())
            }
        }

        playerProfilView1.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("1")
        }

        playerProfilView2.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("2")
        }

        playerProfilView3.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("3")
        }

        playerProfilView4.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("4")
        }

        playerProfilView5.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("5")
        }

        playerProfilView6.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("6")
        }

        playerProfilView7.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("7")
        }

        playerProfilView8.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onRebuyPlayer("8")
        }

        buttonStartGame.setOnClickListener {
            textViewErrorNotEnoughtPlayer.visibility = View.GONE
            viewModel.onStartGame()
        }

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

        buttonEndGame.setOnClickListener {
            ConfirmationEndGameDialog.newInstance()
                .show(supportFragmentManager, "ConfirmationEndGameDialog")
        }

        buttonCustomStack.setOnClickListener {
            viewModel.onCustomStack()
        }
    }

    private fun showDialogCustomStack(gameUiModel: GameUiModel.ShowCustomStackDialog) {
        CustomStackDialog.newInstance(gameUiModel).show(supportFragmentManager, "CustomStackDialog")
    }

    fun onClickOnCheckInConfirmationEndGameDialog() {
        viewModel.onEndGame()
    }

    override fun onBackPressed() {
        // do nothing
    }

    fun onDismissEndGameDialog() {
        viewModel.deleteGame()
        goBackToWelcomeActivity()
    }

    private fun goBackToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun onCheckCustomStackDialog(playerList: List<PlayerCustomStackUiModel>) {
        viewModel.onAddOrWithdrawStack(playerList)
    }
}
