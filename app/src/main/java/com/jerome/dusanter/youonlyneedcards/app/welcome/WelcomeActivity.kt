package com.jerome.dusanter.youonlyneedcards.app.welcome

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jerome.dusanter.youonlyneedcards.R
import kotlinx.android.synthetic.main.activity_main.*

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
        setupListeners()
    }

    private fun setupListeners() {
        buttonNewGame.setOnClickListener {
            viewModel.onNewGame(this)
        }

        buttonOldGame.setOnClickListener {
            viewModel.onOldGame()
        }
    }
}
