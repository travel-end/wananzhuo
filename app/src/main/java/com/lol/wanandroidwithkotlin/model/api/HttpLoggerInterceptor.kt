package com.lol.wanandroidwithkotlin.model.api

import com.lol.wanandroidwithkotlin.ext.loge
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Create by Jingui on 2020/6/12
 * Describe:
 * email: m15008497107@163.com
 */
class HttpLoggerInterceptor: HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        loge(message)
    }
}