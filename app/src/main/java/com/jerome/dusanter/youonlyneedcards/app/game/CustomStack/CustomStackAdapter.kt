package com.jerome.dusanter.youonlyneedcards.app.game.CustomStack

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerCustomStackUiModel
import com.jerome.dusanter.youonlyneedcards.core.CustomStack
import com.jerome.dusanter.youonlyneedcards.utils.SeekBarChangeListener
import com.jerome.dusanter.youonlyneedcards.utils.transformIntoDecade
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.radioButtonAdd
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.radioButtonWithdraw
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.seekBarStack
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.textViewName
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.textViewResult
import kotlinx.android.synthetic.main.item_recycler_view_dialog_custom_stack.view.textViewStack

class CustomStackAdapter(
    private val context: Context?,
    private val items: List<PlayerCustomStackUiModel>
) : RecyclerView.Adapter<CustomStackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomStackViewHolder {
        return CustomStackViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_recycler_view_dialog_custom_stack,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomStackViewHolder, position: Int) {
        holder.bind(items[position], context)
    }
}

class CustomStackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(uiModel: PlayerCustomStackUiModel, context: Context?) {
        itemView.textViewName.text = uiModel.name
        itemView.radioButtonAdd.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemView.textViewResult.text =
                    context?.getString(R.string.poker_activity_custom_stack_add)
                uiModel.action = CustomStack.Add
            }
        }
        itemView.radioButtonWithdraw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                itemView.textViewResult.text =
                    context?.getString(R.string.poker_activity_custom_stack_withdraw)
                uiModel.action = CustomStack.Withdraw
            }
        }
        itemView.seekBarStack.max = uiModel.stack
        uiModel.stack = 0
        itemView.textViewStack.text = context?.getString(
            R.string.poker_activity_number_chips,
            uiModel.stack
        )
        itemView.seekBarStack.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                uiModel.stack = progress.transformIntoDecade()
                itemView.textViewStack.text = context?.getString(
                    R.string.poker_activity_number_chips,
                    uiModel.stack
                )
            }
        })
    }
}
