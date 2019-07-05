package com.jerome.dusanter.youonlyneedcards.app.game

import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import kotlinx.android.synthetic.main.layout_dialog_raise.*

class RaiseDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_dialog_raise, container, false)

    var stackRaised = 0
    var isAllIn = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonMinRaise.text = (arguments[EXTRA_BIG_BLIND] as Int).toString()
        seekBarRaise.progress = arguments[EXTRA_BIG_BLIND] as Int
        stackRaised = arguments[EXTRA_BIG_BLIND] as Int
        setupListeners()
    }

    private fun setupListeners() {

        seekBarRaise.max = arguments[EXTRA_STACK_PLAYER] as Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarRaise.min = arguments[EXTRA_BIG_BLIND] as Int
        }
        seekBarRaise.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                setTextViewMoneyToRaise(progress)
                stackRaised = progress
                isAllIn = progress == arguments[EXTRA_STACK_PLAYER] as Int
            }
        })
        buttonAllin.setOnClickListener {
            setTextViewMoneyToRaise(arguments[EXTRA_STACK_PLAYER] as Int)
            seekBarRaise.progress = arguments[EXTRA_STACK_PLAYER] as Int
        }

        buttonMinRaise.setOnClickListener {
            setTextViewMoneyToRaise(arguments[EXTRA_BIG_BLIND] as Int)
            seekBarRaise.progress = arguments[EXTRA_BIG_BLIND] as Int
        }

        imageButtonCheck.setOnClickListener {
            dismiss()
            val activity = activity as GameActivity
            activity.onRaise(isAllIn, stackRaised)
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }

    }

    private fun setTextViewMoneyToRaise(stack: Int) {
        textViewMoneyToRaise.text = getString(R.string.poker_activity_stack_raise, stack)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(
            resources.getDimension(R.dimen.dialog_raise_width).toInt(),
            WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        private const val EXTRA_BIG_BLIND = "EXTRA_BIG_BLIND"
        private const val EXTRA_STACK_PLAYER = "EXTRA_STACK_PLAYER"

        fun newInstance(dialogRaiseUiModel: DialogRaiseUiModel): RaiseDialog {
            val args = Bundle()
            args.putInt(EXTRA_BIG_BLIND, dialogRaiseUiModel.bigBlind)
            args.putInt(EXTRA_STACK_PLAYER, dialogRaiseUiModel.stackPlayer)
            val dialog = RaiseDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
