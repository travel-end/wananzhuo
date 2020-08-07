package com.lol.wanandroidwithkotlin.ui.share

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class ShareRepository {
    suspend fun shareArticle(title: String, link: String) =
        RetrofitClient.apiService.shareArticle(title, link).apiData()
}