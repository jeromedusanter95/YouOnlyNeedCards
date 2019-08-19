package com.jerome.dusanter.youonlyneedcards.app.game.playerprofile

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.jerome.dusanter.youonlyneedcards.R.drawable
import com.jerome.dusanter.youonlyneedcards.R.layout
import com.jerome.dusanter.youonlyneedcards.core.ActionPlayer
import com.jerome.dusanter.youonlyneedcards.core.StatePlayer
import kotlinx.android.synthetic.main.layout_profil_player_view.view.*


class PlayerProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, layout.layout_profil_player_view, this)
        setupListeners()
        changeBackgroundColor(
            PlayerProfileUiModel.ShowPlayer(
                statePlayer = "Playing",
                name = "",
                stateBlind = "Nothing",
                stack = "",
                actionPlayer = "Nothing"
            )
        )
    }

    private fun setupListeners() {
        constraintLayoutAddPlayer.setOnClickListener {
            constraintLayoutEditPlayer.visibility = View.VISIBLE
            constraintLayoutAddPlayer.visibility = View.GONE
        }
        imageButtonClose.setOnClickListener {
            constraintLayoutEditPlayer.visibility = View.GONE
            constraintLayoutAddPlayer.visibility = View.VISIBLE
        }
    }

    private fun showPlayerLayout() {
        constraintLayoutAddPlayer.visibility = View.GONE
        constraintLayoutEditPlayer.visibility = View.GONE
        constraintLayoutRebuyPlayer.visibility = View.GONE
        constraintLayoutShowPlayer.visibility = View.VISIBLE
    }

    fun getName(): String {
        return editTextAddPlayer.text.toString()
    }

    fun updateProfilePlayer(uiModel: PlayerProfileUiModel) {
        if (uiModel is PlayerProfileUiModel.ShowPlayer) {
            showPlayerLayout()
            textViewName.text = uiModel.name
            textViewStack.text = uiModel.stack
            textViewStatePlayer.text = uiModel.actionPlayer
            textViewStateBlind.text = uiModel.stateBlind
            changeBackgroundColor(uiModel)
        } else if (uiModel is PlayerProfileUiModel.ShowRebuy) {
            constraintLayoutRebuyPlayer.visibility = View.VISIBLE
            constraintLayoutShowPlayer.visibility = View.GONE
            textViewPlayerNameEliminate.text = uiModel.name
            changeBackgroundColor(uiModel)
        }
    }

    private fun changeBackgroundColor(uiModel: PlayerProfileUiModel) {
        if (uiModel is PlayerProfileUiModel.ShowPlayer && uiModel.actionPlayer == ActionPlayer.Fold.name) {
            background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(
                    drawable.background_player_profile_view_fold,
                    null
                )
            } else {
                resources.getDrawable(
                    drawable.background_player_profile_view_fold
                )
            }
        } else if (uiModel is PlayerProfileUiModel.ShowPlayer) {
            background = when (uiModel.statePlayer) {
                StatePlayer.CurrentTurn.name -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    resources.getDrawable(
                        drawable.background_player_profile_view_current_turn,
                        null
                    )
                } else {
                    resources.getDrawable(
                        drawable.background_player_profile_view_current_turn
                    )
                }
                StatePlayer.Eliminate.name -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    resources.getDrawable(
                        drawable.background_player_profile_view_eliminate,
                        null
                    )
                } else {
                    resources.getDrawable(
                        drawable.background_player_profile_view_eliminate
                    )
                }
                else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    resources.getDrawable(
                        drawable.background_player_profile_view_add_edit_play,
                        null
                    )
                } else {
                    resources.getDrawable(
                        drawable.background_player_profile_view_add_edit_play
                    )
                }
            }
        } else {
            background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(
                    drawable.background_player_profile_view_eliminate,
                    null
                )
            } else {
                resources.getDrawable(
                    drawable.background_player_profile_view_eliminate
                )
            }
        }
    }
}

sealed class PlayerProfileUiModel {
    data class ShowPlayer(
        val name: String,
        val stack: String,
        val stateBlind: String,
        val statePlayer: String,
        val actionPlayer: String
    ) : PlayerProfileUiModel()

    data class ShowRebuy(
        val name: String
    ) : PlayerProfileUiModel()
}





