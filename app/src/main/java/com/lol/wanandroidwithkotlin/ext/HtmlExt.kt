package com.lol.wanandroidwithkotlin.ext

import androidx.core.text.HtmlCompat

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this,HtmlCompat.FROM_HTML_MODE_LEGACY)

infix fun String.begin(prefix:String):Boolean =startsWith(prefix)

