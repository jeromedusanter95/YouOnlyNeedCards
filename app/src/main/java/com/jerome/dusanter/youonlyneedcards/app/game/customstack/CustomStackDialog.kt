package com.jerome.dusanter.youonlyneedcards.app.game.customstack

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.GameActivity
import com.jerome.dusanter.youonlyneedcards.app.game.GameUiModel
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerCustomStackUiModel
import kotlinx.android.synthetic.main.dialog_custom_stack.*
import java.io.Serializable


class CustomStackDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_custom_stack,
        container,
        false
    )

    private var playerList = mutableListOf<PlayerCustomStackUiModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerList = arguments?.get(EXTRA_PlAYER_CUSTOM_STACK_LIST) as MutableList<PlayerCustomStackUiModel>
        setupRecycler(view.context)
        setupListeners()
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun setupRecycler(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = CustomStackAdapter(
            context,
            playerList
        )
    }

    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
            (activity as GameActivity).onCheckCustomStackDialog(playerList)
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

    companion object {
        private const val EXTRA_PlAYER_CUSTOM_STACK_LIST = "EXTRA_PlAYER_CUSTOM_STACK_LIST"

        fun newInstance(uiModel: GameUiModel.ShowCustomStackDialog): CustomStackDialog {
            val args = Bundle()
            args.putSerializable(
                EXTRA_PlAYER_CUSTOM_STACK_LIST,
                uiModel.playerCustomStackList as Serializable
            )
            val dialog = CustomStackDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
