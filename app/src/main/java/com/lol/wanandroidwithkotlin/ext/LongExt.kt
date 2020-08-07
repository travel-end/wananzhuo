package com.lol.wanandroidwithkotlin.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
fun Long.toDateTime(pattern:String):String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)