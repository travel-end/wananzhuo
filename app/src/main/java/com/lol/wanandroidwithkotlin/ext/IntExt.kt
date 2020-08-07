package com.lol.wanandroidwithkotlin.ext

import com.lol.wanandroidwithkotlin.MyApp


/**
 * Created by xiaojianjun on 2019-12-02.
 */
fun Int.toPx() = dpToPx(MyApp.instance, this.toFloat())

fun Int.toIntPx() = dpToPx(MyApp.instance, this.toFloat()).toInt()

fun Int.toDp() = pxToDp(MyApp.instance, this.toFloat())
fun Int.toIntDp() = pxToDp(MyApp.instance, this.toFloat()).toInt()