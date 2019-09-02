package com.jerome.dusanter.youonlyneedcards.app.game.endgame

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.jerome.dusanter.youonlyneedcards.R
import kotlinx.android.synthetic.main.dialog_confirmation_end_game.*
import java.io.Serializable


class ConfirmationEndGameDialogFragment : DialogFragment() {

    private var listener: Listener? = null

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
        listener = arguments?.get(EXTRA_CONFIRMATION_END_GAME_DIALOG_LISTENER) as Listener
        setupListeners()
    }


    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
            listener?.onDismissConfirmationEndGameDialogFragment()
        }

        imageButtonClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    interface Listener : Serializable {
        fun onDismissConfirmationEndGameDialogFragment()
    }

    companion object {
        private const val EXTRA_CONFIRMATION_END_GAME_DIALOG_LISTENER =
            "EXTRA_CONFIRMATION_END_GAME_DIALOG_LISTENER"

        fun newInstance(listener: Listener): ConfirmationEndGameDialogFragment {
            val args = Bundle()
            args.putSerializable(
                EXTRA_CONFIRMATION_END_GAME_DIALOG_LISTENER,
                listener
            )
            val dialog = ConfirmationEndGameDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }
}
