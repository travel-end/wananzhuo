package com.lol.wanandroidwithkotlin.common

import android.widget.SeekBar

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SeekBarChangeListenerAdapter(
    private val onProgressChanged: ((seekBar: SeekBar?, progress: Int, fromUser: Boolean) -> Unit)? = null,
    private val onStartTrackingTouch: ((seekBar: SeekBar?) -> Unit)? = null,
    private val onStopTrackingTouch: ((seekBar: SeekBar?) -> Unit)? = null
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgressChanged?.invoke(seekBar, progress, fromUser)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        onStartTrackingTouch?.invoke(seekBar)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        onStopTrackingTouch?.invoke(seekBar)
    }

}