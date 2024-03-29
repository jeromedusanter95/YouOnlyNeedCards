package com.jerome.dusanter.youonlyneedcards.app.game.endturn

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
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerEndTurnUiModel
import kotlinx.android.synthetic.main.dialog_end_turn.*
import java.io.Serializable


class EndTurnDialogFragment : DialogFragment() {

    private var listener: Listener? = null

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
        listener = arguments?.get(EXTRA_END_TURN_DIALOG_LISTENER) as Listener
        setupRecycler(
            view.context,
            arguments?.get(EXTRA_PLAYER_END_TURN_LIST) as MutableList<PlayerEndTurnUiModel>
        )
        setupListeners()
    }

    private fun setupRecycler(
        context: Context,
        playerEndTurnUiModelList: List<PlayerEndTurnUiModel>
    ) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = EndTurnAdapter(
            context,
            playerEndTurnUiModelList
        )
    }

    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            dismiss()
            listener?.onDismissEndTurnDialogFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(
            resources.getDimension(R.dimen.dialog_end_turn_width).toInt(),
            WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    interface Listener : Serializable {
        fun onDismissEndTurnDialogFragment()
    }

    companion object {
        private const val EXTRA_PLAYER_END_TURN_LIST = "EXTRA_PLAYER_END_TURN_LIST"
        private const val EXTRA_END_TURN_DIALOG_LISTENER = "EXTRA_END_TURN_DIALOG_LISTENER"

        fun newInstance(
            uiModel: GameUiModel.ShowEndTurnDialog,
            listener: Listener
        ): EndTurnDialogFragment {
            val args = Bundle()
            args.putSerializable(
                EXTRA_PLAYER_END_TURN_LIST,
                uiModel.playerEndTurnList as Serializable
            )
            args.putSerializable(EXTRA_END_TURN_DIALOG_LISTENER, listener)
            val dialog = EndTurnDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }
}
