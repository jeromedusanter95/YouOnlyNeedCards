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
import com.jerome.dusanter.youonlyneedcards.core.Winner
import kotlinx.android.synthetic.main.dialog_choose_winners.imageButtonCheck
import kotlinx.android.synthetic.main.dialog_choose_winners.recyclerView
import kotlinx.android.synthetic.main.dialog_choose_winners.textViewError
import kotlinx.android.synthetic.main.dialog_choose_winners.textViewResult
import java.io.Serializable


class ChooseWinnersDialog : DialogFragment() {

    //TODO pour l'instant on gère que le cas d'un seul pot !
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_choose_winners,
        container,
        false
    )

    var potList = mutableListOf<PotUiModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        potList = arguments[EXTRA_POT_LIST] as MutableList<PotUiModel>
        setupRecycler(view.context)
        setupListeners()
        textViewResult.text = getString(
            R.string.poker_activity_choose_winners_current_pot_stack,
            potList[0].stack
        )
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun setupListeners() {
        imageButtonCheck.setOnClickListener {
            val winnerList = ChooseWinnersMapper().map(potList[0])
            if (winnerList.isNotEmpty()) {
                (activity as GameActivity).onDistributeStack(ChooseWinnersMapper().map(potList[0]))
                dismiss()
            } else {
                textViewError.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun setupRecycler(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val adapter = ChooseWinnersAdapter(
            potList[0],
            context,
            buildChooseWinnersAdapterListener(context)
        )
        recyclerView.adapter = adapter
    }

    private fun buildChooseWinnersAdapterListener(context: Context): ChooseWinnersAdapter.Listener =
        object : ChooseWinnersAdapter.Listener {
            override fun onChecked(pot: PotUiModel) {
                potList[0] = pot
                recyclerView.adapter = ChooseWinnersAdapter(
                    pot,
                    context,
                    buildChooseWinnersAdapterListener(context)
                )
            }
        }

    companion object {
        private const val EXTRA_POT_LIST = "EXTRA_POT_LIST"

        fun newInstance(uiModel: GameUiModel.ShowChooseWinnersDialog): ChooseWinnersDialog {
            val args = Bundle()
            args.putSerializable(EXTRA_POT_LIST, uiModel.potList as Serializable)
            val dialog = ChooseWinnersDialog()
            dialog.arguments = args
            return dialog
        }
    }
}


class ChooseWinnersMapper {
    fun map(pot: PotUiModel): List<Winner> {
        return pot.potentialWinnerList.filter { it.isWinner }.map {
            Winner(it.id, pot.stackForEachPlayer)
        }
    }
}
