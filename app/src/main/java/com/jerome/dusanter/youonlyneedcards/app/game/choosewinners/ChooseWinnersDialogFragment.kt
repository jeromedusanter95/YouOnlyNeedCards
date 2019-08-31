package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
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
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_choose_winners.*
import java.io.Serializable
import javax.inject.Inject

class ChooseWinnersDialogFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ChooseWinnersViewModel
    private lateinit var adapter: ChooseWinnersAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ChooseWinnersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.dialog_choose_winners,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupLiveDatas()
        setupRecycler()
        setupView()
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupListeners() {
        imageButton.setOnClickListener {
            viewModel.onClickImageButton()
        }
    }

    private fun setupLiveDatas() {
        viewModel.state.observe(
            this,
            Observer { uiModel ->
                if (uiModel != null) {
                    when (uiModel) {
                        is ChooseWinnersUiModel.Check -> dismissView(uiModel)
                        is ChooseWinnersUiModel.NextPot -> updateView(uiModel)
                        is ChooseWinnersUiModel.Error -> showError()
                        is ChooseWinnersUiModel.RefreshList -> refreshRecyclerView(uiModel.potChooseWinners)
                    }
                }
            })
    }

    private fun setupRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = ChooseWinnersAdapter(context, buildChooseWinnersAdapterListener())
        recyclerView.adapter = adapter
    }

    private fun setupView() {
        val potList = arguments?.get(EXTRA_POT_LIST) as MutableList<PotChooseWinners>
        viewModel.onStartDialog(potList)
    }

    private fun dismissView(uiModel: ChooseWinnersUiModel.Check) {
        (activity as GameActivity).onDismissChooseWinnersDialog(
            uiModel.winnerList
        )
        dismiss()
    }

    private fun updateView(uiModel: ChooseWinnersUiModel.NextPot) {
        textViewError.visibility = View.GONE
        refreshRecyclerView(uiModel.currentPot)
        setupImageButton(uiModel.isImageButtonCheck)
        setupTextViewResult(uiModel.currentPot.stack)
    }

    private fun showError() {
        textViewError.visibility = View.VISIBLE
    }


    private fun buildChooseWinnersAdapterListener(): ChooseWinnersAdapter.Listener =
        object : ChooseWinnersAdapter.Listener {
            override fun onChecked(pot: PotChooseWinners) {
                viewModel.onChoosePlayer(pot)
            }
        }

    private fun refreshRecyclerView(currentPot: PotChooseWinners) {
        adapter.refresh(currentPot)
    }

    private fun setupImageButton(isImageButtonCheck: Boolean) {
        if (isImageButtonCheck) {
            imageButton.setImageResource(R.drawable.ic_check_white_24dp)
        } else {
            imageButton.setImageResource(R.drawable.ic_arrow_forward_black_24dp)
        }
    }

    private fun setupTextViewResult(currentPotStack: Int) {
        textViewResult.text =
            getString(R.string.game_activity_choose_winners_current_pot_stack, currentPotStack)
    }

    companion object {
        private const val EXTRA_POT_LIST = "EXTRA_POT_LIST"

        fun newInstance(uiModel: GameUiModel.ShowChooseWinnersDialog): ChooseWinnersDialogFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_POT_LIST, uiModel.potList as Serializable)
            val dialog = ChooseWinnersDialogFragment()
            dialog.arguments = args
            return dialog
        }
    }
}
