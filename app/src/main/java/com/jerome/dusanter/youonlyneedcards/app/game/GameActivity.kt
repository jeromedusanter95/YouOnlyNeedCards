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
import com.jerome.dusanter.youonlyneedcards.app.Keys
import com.jerome.dusanter.youonlyneedcards.app.game.choosewinners.ChooseWinnersDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.customstack.CustomStackDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.endgame.ConfirmationEndGameDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.endgame.EndGameDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.endturn.EndTurnDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.raise.RaiseDialogFragment
import com.jerome.dusanter.youonlyneedcards.app.game.raise.RaiseUiModel
import com.jerome.dusanter.youonlyneedcards.app.welcome.WelcomeActivity
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.Winner
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.layout_profil_player_view.view.*
import javax.inject.Inject


class GameActivity : AppCompatActivity(),
    RaiseDialogFragment.Listener,
    CustomStackDialogFragment.Listener,
    ChooseWinnersDialogFragment.Listener,
    EndTurnDialogFragment.Listener,
    ConfirmationEndGameDialogFragment.Listener,
    EndGameDialogFragment.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        setupListeners()
        setupLiveDatas()
    }

    private fun setupListeners() {
        playerProfilView1.imageButtonCheck.setOnClickListener {
            if (!playerProfilView1.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "1",
                    playerProfilView1.getName()
                )
            }
        }
        playerProfilView2.imageButtonCheck.setOnClickListener {
            if (!playerProfilView2.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "2",
                    playerProfilView2.getName()
                )
            }
        }
        playerProfilView3.imageButtonCheck.setOnClickListener {
            if (!playerProfilView3.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "3",
                    playerProfilView3.getName()
                )
            }
        }
        playerProfilView4.imageButtonCheck.setOnClickListener {
            if (!playerProfilView4.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "4",
                    playerProfilView4.getName()
                )
            }
        }
        playerProfilView5.imageButtonCheck.setOnClickListener {
            if (!playerProfilView5.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "5",
                    playerProfilView5.getName()
                )
            }
        }

        playerProfilView6.imageButtonCheck.setOnClickListener {
            if (!playerProfilView6.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "6",
                    playerProfilView6.getName()
                )
            }
        }

        playerProfilView7.imageButtonCheck.setOnClickListener {
            if (!playerProfilView7.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "7",
                    playerProfilView7.getName()
                )
            }
        }

        playerProfilView8.imageButtonCheck.setOnClickListener {
            if (!playerProfilView8.getName().isBlank()) {
                viewModel.onClickImageButtonCheckProfilePlayerView(
                    "8",
                    playerProfilView8.getName()
                )
            }
        }

        playerProfilView1.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("1")
        }

        playerProfilView2.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("2")
        }

        playerProfilView3.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("3")
        }

        playerProfilView4.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("4")
        }

        playerProfilView5.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("5")
        }

        playerProfilView6.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("6")
        }

        playerProfilView7.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("7")
        }

        playerProfilView8.constraintLayoutRebuyPlayer.setOnClickListener {
            viewModel.onClickRebuyLayoutProfilePlayerView("8")
        }

        buttonStartGame.setOnClickListener {
            textViewErrorNotEnoughtPlayer.visibility = View.GONE
            viewModel.onClickButtonStartGame()
        }

        buttonLeft.setOnClickListener {
            viewModel.onClickButtonLeft()
        }

        buttonMiddle.setOnClickListener {
            viewModel.onClickButtonMiddle()
        }

        buttonRight.setOnClickListener {
            viewModel.onClickButtonRight()
        }

        buttonStartTurn.setOnClickListener {
            viewModel.onClickButtonStartTurn()
        }

        buttonEndGame.setOnClickListener {
            showConfirmationEndGameDialogFragment()
        }

        buttonCustomStack.setOnClickListener {
            viewModel.onClickButtonCustomStack()
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
                        is GameUiModel.ShowChooseWinnersDialog -> updateViewBetweenTurn(
                            gameUiModel
                        )
                        is GameUiModel.ShowCurrentTurn -> updateViewDuringTurn(gameUiModel)
                        is GameUiModel.ShowEndTurnDialog -> showEndTurnDialogFragment(gameUiModel)
                        is GameUiModel.ShowEndGameDialog -> showEndGameDialogFragment(gameUiModel)
                        is GameUiModel.ShowErrorNotEnoughtPlayer -> showErrorNotEnoughPlayer()
                        is GameUiModel.ShowRaiseDialog -> showRaiseDialogFragment(
                            gameUiModel.bigBlind,
                            gameUiModel.stackPlayer
                        )
                        is GameUiModel.ShowCustomStackDialog -> showCustomStackDialogFragment(
                            gameUiModel
                        )
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart(intent.getBooleanExtra(Keys.RESUME_GAME, false))
    }

    private fun showErrorNotEnoughPlayer() {
        textViewErrorNotEnoughtPlayer.visibility = View.VISIBLE
    }

    private fun showRaiseDialogFragment(bigBlind: Int, stackPlayer: Int) {
        RaiseDialogFragment
            .newInstance(bigBlind, stackPlayer, this)
            .show(supportFragmentManager, "RaiseDialogFragment")
    }

    private fun showCustomStackDialogFragment(gameUiModel: GameUiModel.ShowCustomStackDialog) {
        CustomStackDialogFragment
            .newInstance(gameUiModel, this)
            .show(supportFragmentManager, "CustomStackDialogFragment")
    }

    private fun showChooseWinnersDialogFragment(gameUiModel: GameUiModel.ShowChooseWinnersDialog) {
        ChooseWinnersDialogFragment
            .newInstance(gameUiModel, this)
            .show(supportFragmentManager, "ChooseWinnersDialogFragment")
    }

    private fun showEndTurnDialogFragment(gameUiModel: GameUiModel.ShowEndTurnDialog) {
        EndTurnDialogFragment
            .newInstance(gameUiModel, this)
            .show(supportFragmentManager, "EndTurnDialogFragment")
        viewModel.onShowEndTurnDialog()
    }

    private fun showConfirmationEndGameDialogFragment() {
        ConfirmationEndGameDialogFragment
            .newInstance(this)
            .show(supportFragmentManager, "ConfirmationEndGameDialogFragment")
    }

    private fun showEndGameDialogFragment(gameDialogUiModel: GameUiModel.ShowEndGameDialog) {
        EndGameDialogFragment
            .newInstance(gameDialogUiModel, this)
            .show(supportFragmentManager, "EndGameDialogFragment")
    }

    override fun onDismissRaiseDialogFragment(uiModel: RaiseUiModel.Check) {
        if (uiModel.isAllin) {
            viewModel.onDismissRaiseDialog(ActionPlayer.AllIn, uiModel.stackRaised)
        } else {
            viewModel.onDismissRaiseDialog(ActionPlayer.Raise, uiModel.stackRaised)
        }
    }

    override fun onDismissCustomStackDialogFragment(playerList: List<PlayerCustomStackUiModel>) {
        viewModel.onDismissCustomStackDialog(playerList)
    }

    override fun onDismissChooseWinnersDialogFragment(winnerList: List<Winner>) {
        viewModel.onDismissChooseWinnersDialog(winnerList)
    }

    override fun onDismissEndTurnDialogFragment() {
        viewModel.onDismissEndTurnDialog()
    }

    override fun onDismissConfirmationEndGameDialogFragment() {
        viewModel.onDismissConfirmationEndGameDialog()
    }

    override fun onDismissEndGameDialogFragment() {
        viewModel.onDismissEndGameDialog()
        goBackToWelcomeActivity()
    }

    private fun updateViewDuringTurn(gameUiModel: GameUiModel.ShowCurrentTurn) {
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

    private fun updateViewBetweenTurn(gameUiModel: GameUiModel.ShowChooseWinnersDialog) {
        groupBottomButtonsBetweenTurn.visibility = View.VISIBLE
        groupBottomButtonsDuringTurn.visibility = View.GONE
        buttonRight.visibility = View.GONE
        groupTextViewsDuringTurn.visibility = View.GONE
        if (gameUiModel.shouldShowChooseWinnersDialog) {
            showChooseWinnersDialogFragment(gameUiModel)
        } else {
            viewModel.notShowChooseWinnersDialog(gameUiModel.potList[0])
        }
    }

    private fun startTimer(durationInMillis: Long) {
        object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.onTimerTick(millisUntilFinished)
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
                viewModel.onTimerFinish()
            }
        }.start()
    }

    private fun goBackToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        // do nothing
    }
}
