package com.lol.wanandroidwithkotlin.model.api

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
data class ApiResult<T>(val errorCode: Int, val errorMsg: String, private val data: T) {
    fun apiData(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode,errorMsg)
        }
    }
}