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
import com.jerome.dusanter.youonlyneedcards.app.game.GameUiModel
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerCustomStackUiModel
import kotlinx.android.synthetic.main.dialog_custom_stack.*
import java.io.Serializable


class CustomStackDialogFragment : DialogFragment() {

    private var playerList = mutableListOf<PlayerCustomStackUiModel>()
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_custom_stack,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerList = arguments?.get(
            EXTRA_PLAYER_CUSTOM_STACK_LIST
        ) as MutableList<PlayerCustomStackUiModel>
        listener = arguments?.get(EXTRA_CUSTOM_STACK_DIALOG_LISTENER) as Listener
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
            listener?.onDismissCustomStackDialogFragment(playerList)
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
        fun onDismissCustomStackDialogFragment(playerList: List<PlayerCustomStackUiModel>)
    }

    companion object {
        private const val EXTRA_PLAYER_CUSTOM_STACK_LIST = "EXTRA_PLAYER_CUSTOM_STACK_LIST"
        private const val EXTRA_CUSTOM_STACK_DIALOG_LISTENER = "EXTRA_CUSTOM_STACK_DIALOG_LISTENER"

        fun newInstance(
            uiModel: GameUiModel.ShowCustomStackDialog,
            listener: Listener
        ): CustomStackDialogFragment {
            val args = Bundle()
            args.putSerializable(
                EXTRA_PLAYER_CUSTOM_STACK_LIST,
                uiModel.playerCustomStackList as Serializable
            )
            args.putSerializable(EXTRA_CUSTOM_STACK_DIALOG_LISTENER, listener)
            val dialog = CustomStackDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }
}
