package com.jerome.dusanter.youonlyneedcards.app.game

import android.app.DialogFragment
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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


class EndTurnDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_end_turn,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(
            view.context,
            arguments[EXTRA_PLAYER_END_TURN_LIST] as MutableList<PlayerEndTurnUiModel>
        )
        setupListeners()
    }

    private fun setupRecycler(
        context: Context,
        playerEndTurnUiModelList: List<PlayerEndTurnUiModel>
    ) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = EndTurnAdapter(context, playerEndTurnUiModelList)
    }

    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
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
        private const val EXTRA_PLAYER_END_TURN_LIST = "EXTRA_PLAYER_END_TURN_LIST"

        fun newInstance(uiModel: GameUiModel.ShowEndTurn): EndTurnDialog {
            val args = Bundle()
            args.putSerializable(
                EXTRA_PLAYER_END_TURN_LIST,
                uiModel.playerEndTurnList as Serializable
            )
            val dialog = EndTurnDialog()
            dialog.arguments = args
            return dialog
        }
    }
}
