package com.jerome.dusanter.youonlyneedcards.utils

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import kotlin.math.round

abstract class SeekBarChangeListener : OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}

fun Int.transformIntoDecade(): Int {
    return (round((this / 10).toDouble()) * 10).toInt()
}
