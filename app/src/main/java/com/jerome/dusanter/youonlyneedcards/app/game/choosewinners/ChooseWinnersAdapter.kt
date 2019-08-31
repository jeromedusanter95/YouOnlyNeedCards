package com.jerome.dusanter.youonlyneedcards.app.game.choosewinners

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jerome.dusanter.youonlyneedcards.R
import kotlinx.android.synthetic.main.item_recycler_view_dialog_choose_winners.view.*

class ChooseWinnersAdapter(
    private val context: Context?,
    private val listener: Listener
) : RecyclerView.Adapter<ChooseWinnersViewHolder>() {

    var pot: PotChooseWinners = PotChooseWinners(
        listOf(),
        0,
        0
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseWinnersViewHolder {
        return ChooseWinnersViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_recycler_view_dialog_choose_winners,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int =
        if (pot.potentialWinnerList.isNotEmpty()) pot.potentialWinnerList.size else 0

    override fun onBindViewHolder(holderChooseWinners: ChooseWinnersViewHolder, position: Int) {
        holderChooseWinners.bind(pot.potentialWinnerList[position], pot.stackForEachPlayer)
        holderChooseWinners.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
            pot.potentialWinnerList[position].isWinner = isChecked
            if (pot.potentialWinnerList.any { it.isWinner }) {
                pot.stackForEachPlayer =
                    pot.stack / pot.potentialWinnerList.filter { it.isWinner }.size
            } else {
                pot.stackForEachPlayer = 0
            }
            listener.onChecked(pot)
        }
    }

    fun refresh(currentPot: PotChooseWinners) {
        pot = currentPot
        notifyDataSetChanged()
    }

    interface Listener {
        fun onChecked(pot: PotChooseWinners)
    }
}

class ChooseWinnersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(player: PlayerChooseWinners, stackEachPlayer: Int) {
        itemView.textViewName.text = player.name
        if (player.isWinner) {
            itemView.textViewStack.text = itemView.context.getString(
                R.string.game_activity_number_chips,
                stackEachPlayer
            )
        } else {
            itemView.textViewStack.text = itemView.context.getString(
                R.string.game_activity_number_chips,
                0
            )
        }
        itemView.checkbox.isChecked = player.isWinner
    }
}
