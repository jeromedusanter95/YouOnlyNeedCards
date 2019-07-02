package com.jerome.dusanter.youonlyneedcards.app.game

import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.jerome.dusanter.youonlyneedcards.R

class DialogEndTurn : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_dialog_end_turn, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        private const val EXTRA_BIG_BLIND = "EXTRA_BIG_BLIND"
        private const val EXTRA_STACK_PLAYER = "EXTRA_STACK_PLAYER"

        fun newInstance(uiModel: GameUiModel.ShowEndTurn): DialogEndTurn {
            val args = Bundle()
            val dialog = DialogEndTurn()
            dialog.arguments = args
            return dialog
        }
    }
}
