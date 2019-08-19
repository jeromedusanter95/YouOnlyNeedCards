package com.jerome.dusanter.youonlyneedcards.app.game.raise

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import com.jerome.dusanter.youonlyneedcards.utils.transformIntoDecade
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_raise.*
import javax.inject.Inject


class RaiseDialogFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RaiseViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RaiseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        com.jerome.dusanter.youonlyneedcards.R.layout.dialog_raise,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stackPlayer = arguments?.get(EXTRA_STACK_PLAYER) as Int
        val bigBlind = arguments?.get(EXTRA_BIG_BLIND) as Int
        setupView(stackPlayer, bigBlind)
        setupLiveData()
        setupListeners()
        viewModel.onStart(bigBlind = bigBlind, stackPlayer = stackPlayer)
    }

    private fun setupView(stackPlayer: Int, bigBlind: Int) {
        seekBarRaise.max = stackPlayer
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarRaise.min = bigBlind
        }
        buttonMinRaise.text = bigBlind.toString()
    }

    private fun setupLiveData() {
        viewModel.state.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    when (uiModel) {
                        is RaiseUiModel.Update -> updateView(uiModel)
                        is RaiseUiModel.Check -> finishDialogWithCheck(uiModel)
                    }
                }
            })
    }

    private fun setupListeners() {
        seekBarRaise.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.onSeekBarRaiseChanged(progress.transformIntoDecade())
            }
        })
        buttonAllin.setOnClickListener {
            viewModel.onAllIn()
        }

        buttonMinRaise.setOnClickListener {
            viewModel.onMinRaise()
        }

        imageButtonCheck.setOnClickListener {
            viewModel.onCheck()
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }

    }

    private fun updateView(uiModel: RaiseUiModel.Update) {
        textViewMoneyToRaise.text = uiModel.stackRaised
    }

    private fun finishDialogWithCheck(uiModel: RaiseUiModel.Check) {
        dismiss()
        val activity = activity as GameActivity
        activity.onDismissRaiseDialog(uiModel.isAllin, uiModel.stackRaised)
    }


    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(
            resources.getDimension(com.jerome.dusanter.youonlyneedcards.R.dimen.dialog_raise_width).toInt(),
            WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        private const val EXTRA_BIG_BLIND = "EXTRA_BIG_BLIND"
        private const val EXTRA_STACK_PLAYER = "EXTRA_STACK_PLAYER"

        fun newInstance(bigBlind: Int, stackPlayer: Int): RaiseDialogFragment {
            val args = Bundle()
            args.putInt(EXTRA_BIG_BLIND, bigBlind)
            args.putInt(EXTRA_STACK_PLAYER, stackPlayer)
            val dialog = RaiseDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }
}
