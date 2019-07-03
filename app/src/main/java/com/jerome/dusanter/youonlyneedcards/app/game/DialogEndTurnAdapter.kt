package com.jerome.dusanter.youonlyneedcards.app.game

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.core.Pot

class DialogEndTurnAdapter :
    ListAdapter<Pot, DialogEndTurnViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pot>() {
            override fun areItemsTheSame(item1: Pot, item2: Pot): Boolean {
                return item1.potentialWinners == item2.potentialWinners
            }

            override fun areContentsTheSame(item1: Pot, item2: Pot): Boolean {
                return item1 == item2
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogEndTurnViewHolder {
        return DialogEndTurnViewHolder.makeHolder(parent)
    }

    override fun onBindViewHolder(holder: DialogEndTurnViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DialogEndTurnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun makeHolder(
            parent: ViewGroup
        ): DialogEndTurnViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_player_dialog_end_turn, parent, false)
            return DialogEndTurnViewHolder(view)
        }
    }

    fun bind(model: Pot) {
        itemView.apply {
        }
    }
}
