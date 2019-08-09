package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jerome.dusanter.youonlyneedcards.R
import kotlinx.android.synthetic.main.activity_main.*

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
        setupListeners()
        setupLiveData()
    }

    private fun setupListeners() {
        buttonNewGame.setOnClickListener {
            viewModel.onNewGame(this)
        }

        buttonOldGame.setOnClickListener {
            viewModel.onOldGame(this)
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
                    }
                }
            }
        )
    }
}
