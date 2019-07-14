package com.jerome.dusanter.youonlyneedcards.app.game

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
import kotlinx.android.synthetic.main.dialog_end_turn.imageButtonCheck
import kotlinx.android.synthetic.main.dialog_end_turn.recyclerView
import java.io.Serializable


class EndGameDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_end_game,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(
            view.context,
            arguments?.get(EXTRA_PLAYER_END_GAME_LIST) as MutableList<PlayerEndGameUiModel>
        )
        setupListeners()
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun setupRecycler(
        context: Context,
        playerEndGameUiModelList: List<PlayerEndGameUiModel>
    ) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = EndGameAdapter(context, playerEndGameUiModelList)
    }

    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
            (activity as GameActivity).onDismissEndGameDialog()
        }
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
        private const val EXTRA_PLAYER_END_GAME_LIST = "EXTRA_PLAYER_END_GAME_LIST"

        fun newInstance(uiModel: GameUiModel.ShowEndGameDialog): EndGameDialog {
            val args = Bundle()
            args.putSerializable(
                EXTRA_PLAYER_END_GAME_LIST,
                uiModel.playerEndGameList as Serializable
            )
            val dialog = EndGameDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
