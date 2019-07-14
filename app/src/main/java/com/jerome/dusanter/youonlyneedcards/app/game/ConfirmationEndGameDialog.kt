package com.jerome.dusanter.youonlyneedcards.app.game

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.jerome.dusanter.youonlyneedcards.R
import kotlinx.android.synthetic.main.dialog_confirmation_end_game.imageButtonCheck
import kotlinx.android.synthetic.main.dialog_confirmation_end_game.imageButtonClose


class ConfirmationEndGameDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_confirmation_end_game,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }


    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
            (activity as GameActivity).onClickOnCheckInConfirmationEndGameDialog()
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(
            WRAP_CONTENT,
            WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {

        fun newInstance(): ConfirmationEndGameDialog {
            return ConfirmationEndGameDialog()
        }
    }
}
