package com.lol.wanandroidwithkotlin.util.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus


/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
object Bus {

    /**
     * 内联函数 对于频繁调用的方法，提升了性能
     */
    inline fun <reified T> post(channel: String, value: T) = LiveEventBus.get(channel, T::class.java).post(value)

    inline fun <reified T> observe(
        channel: String,
        owner: LifecycleOwner,
        crossinline observer: ((value: T) -> Unit)
    ) = LiveEventBus.get(channel,T::class.java).observe(owner, Observer {
        observer(it)
    })
}