package com.jerome.dusanter.youonlyneedcards.app.game

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.jerome.dusanter.youonlyneedcards.R

class PlayerProfilView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_profil_player_view, this)
    }

    fun changeBackgroundColor() {

    }

    fun updateProfilPlayer() {

    }

    fun clickOnAddPlayer() {

    }

    fun clickOnCheck() {

    }

    fun clickOnClose() {
    }
}