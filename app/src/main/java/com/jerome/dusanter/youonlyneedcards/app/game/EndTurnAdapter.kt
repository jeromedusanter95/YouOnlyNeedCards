package com.jerome.dusanter.youonlyneedcards.app.game

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_recycler_view_dialog_end_turn.view.*

class EndTurnAdapter(
    private val context: Context?,
    private val items: List<PlayerEndTurnUiModel>
) : RecyclerView.Adapter<EndTurnViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndTurnViewHolder {
        return EndTurnViewHolder(
            LayoutInflater.from(context).inflate(
                com.jerome.dusanter.youonlyneedcards.R.layout.item_recycler_view_dialog_end_turn,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EndTurnViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class EndTurnViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(playerEndTurnUiModel: PlayerEndTurnUiModel) {
        itemView.textViewDescription.text = playerEndTurnUiModel.description
    }
}
