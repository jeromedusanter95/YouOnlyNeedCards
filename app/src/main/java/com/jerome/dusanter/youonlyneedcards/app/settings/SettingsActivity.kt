package com.jerome.dusanter.youonlyneedcards.app.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import kotlinx.android.synthetic.main.activity_settings.buttonStartGame
import kotlinx.android.synthetic.main.activity_settings.groupIncreasedBlinds
import kotlinx.android.synthetic.main.activity_settings.groupMoney
import kotlinx.android.synthetic.main.activity_settings.seekBarBlind
import kotlinx.android.synthetic.main.activity_settings.seekBarChips
import kotlinx.android.synthetic.main.activity_settings.seekBarFrequencyIncreaseBlind
import kotlinx.android.synthetic.main.activity_settings.seekBarMoney
import kotlinx.android.synthetic.main.activity_settings.switchIncreaseBlinds
import kotlinx.android.synthetic.main.activity_settings.switchMoney
import kotlinx.android.synthetic.main.activity_settings.textViewBlindAmount
import kotlinx.android.synthetic.main.activity_settings.textViewChipsAmount
import kotlinx.android.synthetic.main.activity_settings.textViewIncreaseBlindAnswer
import kotlinx.android.synthetic.main.activity_settings.textViewIncreaseBlindFrequencyAmount
import kotlinx.android.synthetic.main.activity_settings.textViewMoneyAmount
import kotlinx.android.synthetic.main.activity_settings.textViewMoneyAnswer

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        setupListeners()
        setupLiveData()
        setupSeekBarsColor()
        viewModel.start()
    }

    private fun setupListeners() {

        buttonStartGame.setOnClickListener {
            viewModel.onStartGame()
        }

        //TODO add an extension function for setOnSeekBarChangedListener
        seekBarBlind.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarBlindUpdated(progress)
            }
        })

        seekBarMoney.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarMoneyUpdated(progress)
            }
        })

        seekBarFrequencyIncreaseBlind.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarFrequencyIncreasedBlindUpdated(progress)
            }
        })

        seekBarChips.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarStackUpdated(progress)
            }
        })

        switchMoney.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchMoneyToggled(isChecked)
        }

        switchIncreaseBlinds.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchIncreaseBlindsToggled(isChecked)
        }
    }

    private fun setupLiveData() {
        viewModel.state.observe(
            this,
            Observer { state ->
                updateView(state)
            })
    }

    private fun setupSeekBarsColor() {
        seekBarBlind.progressDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
        seekBarFrequencyIncreaseBlind.progressDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
        seekBarChips.progressDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
        seekBarMoney.progressDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
    }

    private fun updateView(uiModel: SettingsUiModel?) {
        if (uiModel != null) {
            textViewBlindAmount.text = uiModel.smallBlind
            textViewChipsAmount.text = uiModel.stack
            textViewMoneyAnswer.text = uiModel.moneyBetAnswer
            textViewIncreaseBlindAnswer.text = uiModel.increaseBlindsAnswer
            if (uiModel.isMoneyBetEnabled) {
                textViewMoneyAmount.text = uiModel.money
                groupMoney.visibility = View.VISIBLE
            } else {
                groupMoney.visibility = View.GONE
            }
            if (uiModel.isIncreaseBlindsEnabled) {
                textViewIncreaseBlindFrequencyAmount.text = uiModel.frequencyIncreasingBlind
                groupIncreasedBlinds.visibility = View.VISIBLE
            } else {
                groupIncreasedBlinds.visibility = View.GONE
            }
        }
    }

}
