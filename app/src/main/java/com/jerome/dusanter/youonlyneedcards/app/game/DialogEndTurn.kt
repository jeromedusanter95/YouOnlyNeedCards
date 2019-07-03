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
import com.jerome.dusanter.youonlyneedcards.core.Pot
import kotlinx.android.synthetic.main.layout_dialog_end_turn.*
import java.io.Serializable

class DialogEndTurn : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_dialog_end_turn, container, false)

    var potList = mutableListOf<Pot>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        potList = arguments[EXTRA_POT_LIST] as MutableList<Pot>
        setupRecycler(view.context)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupRecycler(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
    }

    companion object {
        private const val EXTRA_POT_LIST = "EXTRA_POT_LIST"

        fun newInstance(uiModel: GameUiModel.ShowEndTurn): DialogEndTurn {
            val args = Bundle()
            args.putSerializable(EXTRA_POT_LIST, uiModel.potList as Serializable)
            val dialog = DialogEndTurn()
            dialog.arguments = args
            return dialog
        }
    }
}
