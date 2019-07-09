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
import com.jerome.dusanter.youonlyneedcards.core.Winner
import kotlinx.android.synthetic.main.dialog_choose_winners.*
import java.io.Serializable


class ChooseWinnersDialog : DialogFragment() {

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
    var winnerList = mutableListOf<Winner>()
    var isImageButtonCheck = false
    lateinit var currentPot: PotUiModel
    lateinit var currentContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        potList = arguments?.get(EXTRA_POT_LIST) as MutableList<PotUiModel>
        currentPot = potList[0]
        currentContext = view.context
        setupRecycler(currentContext)
        setupImageButton()
        setupListeners()
        setupTextView()
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun setupRecycler(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val adapter = ChooseWinnersAdapter(
            currentPot,
            context,
            buildChooseWinnersAdapterListener(context)
        )
        recyclerView.adapter = adapter
    }

    private fun buildChooseWinnersAdapterListener(context: Context): ChooseWinnersAdapter.Listener =
        object : ChooseWinnersAdapter.Listener {
            override fun onChecked(pot: PotUiModel) {
                currentPot = pot
                recyclerView.adapter = ChooseWinnersAdapter(
                    pot,
                    context,
                    buildChooseWinnersAdapterListener(context)
                )
            }
        }

    private fun setupImageButton() {
        isImageButtonCheck = if (isLastPot(currentPot)) {
            imageButton.setImageResource(R.drawable.ic_check_white_24dp)
            true
        } else {
            imageButton.setImageResource(R.drawable.ic_arrow_forward_black_24dp)
            false
        }
    }

    private fun isLastPot(pot: PotUiModel): Boolean {
        return potList.lastIndex == potList.indexOf(pot)
    }

    private fun setupTextView() {
        textViewResult.text = getString(
            R.string.poker_activity_choose_winners_current_pot_stack,
            currentPot.stack
        )
        textViewError.visibility = View.INVISIBLE
    }

    private fun setupListeners() {
        imageButton.setOnClickListener {
            winnerList.addAll(ChooseWinnersMapper().map(currentPot))
            if (winnerList.isNotEmpty()) {
                if (isImageButtonCheck) {
                    (activity as GameActivity).onDismissChooseWinnersDialog(
                        winnerList
                    )
                    dismiss()
                } else {
                    setNextPot()
                }
            } else {
                textViewError.visibility = View.VISIBLE
            }
        }
    }

    private fun setNextPot() {
        val previousPotIndex = potList.indexOf(currentPot)
        currentPot = potList[previousPotIndex + 1]
        setupRecycler(currentContext)
        setupImageButton()
        setupListeners()
        setupTextView()
    }


    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
    fun map(pot: PotUiModel): MutableList<Winner> {
        return pot.potentialWinnerList.filter { it.isWinner }.map {
            Winner(it.id, pot.stackForEachPlayer)
        }.toMutableList()
    }
}
