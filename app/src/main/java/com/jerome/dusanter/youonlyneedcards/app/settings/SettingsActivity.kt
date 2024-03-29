package com.jerome.dusanter.youonlyneedcards.app.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import com.jerome.dusanter.youonlyneedcards.utils.transformIntoDecade
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        setupListeners()
        setupLiveData()
        setupSeekBarsColor()
        viewModel.start()
    }

    private fun setupListeners() {
        buttonStartGame.setOnClickListener {
            viewModel.onStartGame()
        }

        seekBarBlind.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarBlindUpdated(progress.transformIntoDecade())
            }
        })

        seekBarMoney.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarMoneyUpdated(progress.transformIntoDecade())
            }
        })

        seekBarFrequencyIncreaseBlind.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarFrequencyIncreasedBlindUpdated(progress)
            }
        })

        seekBarChips.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarStackUpdated(progress.transformIntoDecade())
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
            Observer { settingsUiModel ->
                when (settingsUiModel) {
                    is SettingsUiModel.Success -> updateView(settingsUiModel)
                    is SettingsUiModel.Error -> showError(settingsUiModel)
                    is SettingsUiModel.GoToGameActivity -> goToGameActivity()
                }
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

    private fun updateView(uiModel: SettingsUiModel.Success?) {
        if (uiModel != null) {
            updateTextViewAlwaysVisible(uiModel)
            updateTextViewNotAlwaysVisible(uiModel)
        }
    }

    private fun updateTextViewAlwaysVisible(uiModel: SettingsUiModel.Success) {
        textViewBlindAmount.text = uiModel.smallBlind
        textViewChipsAmount.text = uiModel.stack
        textViewMoneyAnswer.text = uiModel.moneyBetAnswer
        textViewIncreaseBlindAnswer.text = uiModel.increaseBlindsAnswer
    }

    private fun updateTextViewNotAlwaysVisible(uiModel: SettingsUiModel.Success) {
        hideTextViewBlindsErrorIfShouldNotShowAndVisible(uiModel)
        hideTextViewStackErrorIfShouldNotShowAndVisible(uiModel)
        handleBlockMoneyVisibility(uiModel)
        handleBlockIncreaseBlindsVisibility(uiModel)
    }

    private fun hideTextViewBlindsErrorIfShouldNotShowAndVisible(uiModel: SettingsUiModel.Success) {
        if (!uiModel.showErrorBlinds && textViewErrorBlinds.visibility == View.VISIBLE) {
            textViewErrorBlinds.visibility = View.GONE
        }
    }

    private fun hideTextViewStackErrorIfShouldNotShowAndVisible(uiModel: SettingsUiModel.Success) {
        if (!uiModel.showErrorStack && textViewErrorStack.visibility == View.VISIBLE) {
            textViewErrorStack.visibility = View.GONE
        }
    }

    private fun handleBlockMoneyVisibility(uiModel: SettingsUiModel.Success) {
        if (uiModel.isMoneyBetEnabled) {
            textViewMoneyAmount.text = uiModel.money
            if (!uiModel.showErrorMoney
                && textViewErrorMoney.visibility == View.VISIBLE
            ) {
                textViewErrorMoney.visibility = View.GONE
            }
            groupMoney.visibility = View.VISIBLE
        } else {
            groupMoney.visibility = View.GONE
            textViewErrorMoney.visibility = View.GONE
        }
    }

    private fun handleBlockIncreaseBlindsVisibility(uiModel: SettingsUiModel.Success) {
        if (uiModel.isIncreaseBlindsEnabled) {
            textViewIncreaseBlindFrequencyAmount.text = uiModel.frequencyIncreasingBlind
            if (!uiModel.showErrorFrequencyIncreaseBlinds
                && textViewErrorFrequency.visibility == View.VISIBLE
            ) {
                textViewErrorFrequency.visibility = View.GONE
            }
            groupIncreasedBlinds.visibility = View.VISIBLE
        } else {
            groupIncreasedBlinds.visibility = View.GONE
            textViewErrorFrequency.visibility = View.GONE
        }
    }

    private fun showError(settingsUiModel: SettingsUiModel.Error) {
        if (settingsUiModel.showErrorBlinds) {
            textViewErrorBlinds.visibility = View.VISIBLE
        }
        if (settingsUiModel.showErrorStack) {
            textViewErrorStack.visibility = View.VISIBLE
        }
        if (settingsUiModel.showErrorFrequencyIncreaseBlinds) {
            textViewErrorFrequency.visibility = View.VISIBLE
        }
        if (settingsUiModel.showErrorMoney) {
            textViewErrorMoney.visibility = View.VISIBLE
        }
    }

    private fun goToGameActivity() {
        startActivity(Intent(this, GameActivity::class.java))
    }

}
