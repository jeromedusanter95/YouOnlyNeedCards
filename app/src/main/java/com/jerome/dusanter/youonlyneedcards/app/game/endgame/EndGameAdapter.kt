package com.jerome.dusanter.youonlyneedcards.app.game.endgame

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jerome.dusanter.youonlyneedcards.R
import com.jerome.dusanter.youonlyneedcards.app.game.PlayerEndGameUiModel
import kotlinx.android.synthetic.main.item_recycler_view_dialog_end_game.view.*

class EndGameAdapter(
    private val context: Context?,
    private val items: List<PlayerEndGameUiModel>
) : RecyclerView.Adapter<EndGameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndGameViewHolder {
        return EndGameViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_recycler_view_dialog_end_game,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EndGameViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class EndGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(playerEndGameUiModel: PlayerEndGameUiModel) {
        itemView.textViewDescription.text = playerEndGameUiModel.description
        if (playerEndGameUiModel.money.isNotBlank()) {
            itemView.textViewMoney.visibility = View.VISIBLE
            itemView.textViewMoney.text = playerEndGameUiModel.money
        } else {
            itemView.textViewMoney.visibility = View.INVISIBLE
        }
        itemView.textViewRanking.text = playerEndGameUiModel.ranking
    }
}
