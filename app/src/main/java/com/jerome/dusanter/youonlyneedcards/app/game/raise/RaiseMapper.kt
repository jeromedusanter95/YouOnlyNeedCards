package com.jerome.dusanter.youonlyneedcards.app.game.raise

import android.content.Context
import com.jerome.dusanter.youonlyneedcards.R
import javax.inject.Inject

class RaiseMapper @Inject internal constructor(
    private val context: Context
) {
    fun mapToUpdate(raise: Raise): RaiseUiModel {
        return RaiseUiModel.Update(
            context.getString(
                R.string.game_activity_number_chips,
                raise.stackRaised
            )
        )
    }

    fun mapToCheck(raise: Raise): RaiseUiModel {
        return RaiseUiModel.Check(raise.stackRaised, raise.isAllin)
    }
}
