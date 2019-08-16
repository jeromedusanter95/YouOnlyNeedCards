package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.app.settings.SettingsActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WelcomeViewModel::class.java)
        setupListeners()
        setupLiveData()
    }

    private fun setupListeners() {
        buttonNewGame.setOnClickListener {
            viewModel.onNewGame()
        }

        buttonOldGame.setOnClickListener {
            viewModel.onOldGame()
        }
    }

    private fun setupLiveData() {
        viewModel.state.observe(
            this,
            Observer { welcomeUiModel ->
                if (welcomeUiModel != null) {
                    when (welcomeUiModel) {
                        is WelcomeUiModel.Error -> Toast.makeText(
                            this,
                            getString(R.string.welcome_activity_error_retrieving_old_game),
                            Toast.LENGTH_SHORT
                        ).show()

                        is WelcomeUiModel.GoToGameActivity -> goToGameActivity()
                        is WelcomeUiModel.GoToSettingsActivity -> goToSettingsActivity()
                    }
                }
            }
        )
    }

    private fun goToSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun goToGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("fromWelcome", true)
        startActivity(intent)
    }
}
