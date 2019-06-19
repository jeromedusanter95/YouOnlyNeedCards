package com.jerome.dusanter.youonlyneedcards.app.game

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jerome.dusanter.youonlyneedcards.R

class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
    }
}
