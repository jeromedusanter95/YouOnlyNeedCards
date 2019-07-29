package com.jerome.dusanter.youonlyneedcards.app.game

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
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import com.jerome.dusanter.youonlyneedcards.utils.transformIntoDecade
import kotlinx.android.synthetic.main.dialog_raise.*

class RaiseDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_raise, container, false)

    var stackRaised = 0
    var isAllIn = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonMinRaise.text = (arguments?.get(EXTRA_BIG_BLIND) as Int).toString()
        seekBarRaise.progress = arguments!![EXTRA_BIG_BLIND] as Int
        stackRaised = arguments!![EXTRA_BIG_BLIND] as Int
        setupListeners()
    }

    private fun setupListeners() {

        seekBarRaise.max = arguments?.get(EXTRA_STACK_PLAYER) as Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarRaise.min = arguments!![EXTRA_BIG_BLIND] as Int
        }
        seekBarRaise.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                stackRaised = progress.transformIntoDecade()
                setTextViewMoneyToRaise(stackRaised)
                isAllIn = stackRaised == arguments?.get(EXTRA_STACK_PLAYER) as Int
            }
        })
        buttonAllin.setOnClickListener {
            setTextViewMoneyToRaise(arguments!![EXTRA_STACK_PLAYER] as Int)
            seekBarRaise.progress = arguments!![EXTRA_STACK_PLAYER] as Int
        }

        buttonMinRaise.setOnClickListener {
            setTextViewMoneyToRaise(arguments!![EXTRA_BIG_BLIND] as Int)
            seekBarRaise.progress = arguments!![EXTRA_BIG_BLIND] as Int
        }

        imageButtonCheck.setOnClickListener {
            dismiss()
            val activity = activity as GameActivity
            activity.onDismissRaiseDialog(isAllIn, stackRaised)
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }

    }

    private fun setTextViewMoneyToRaise(stack: Int) {
        textViewMoneyToRaise.text = getString(R.string.poker_activity_number_chips, stack)
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

        fun newInstance(raiseDialogUiModel: RaiseDialogUiModel): RaiseDialog {
            val args = Bundle()
            args.putInt(EXTRA_BIG_BLIND, raiseDialogUiModel.bigBlind)
            args.putInt(EXTRA_STACK_PLAYER, raiseDialogUiModel.stackPlayer)
            val dialog = RaiseDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
