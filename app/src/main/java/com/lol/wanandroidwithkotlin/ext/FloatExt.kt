package com.lol.wanandroidwithkotlin.ext

import com.lol.wanandroidwithkotlin.MyApp


/**
 * Created by xiaojianjun on 2019-12-02.
 */
fun Float.toPx() = dpToPx(MyApp.instance, this)

fun Float.toIntPx() = dpToPx(MyApp.instance, this).toInt()

fun Float.toDp() = pxToDp(MyApp.instance, this)

fun Float.toIntDp() = pxToDp(MyApp.instance, this).toInt()