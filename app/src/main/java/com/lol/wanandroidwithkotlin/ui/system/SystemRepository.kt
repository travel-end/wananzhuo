package com.lol.wanandroidwithkotlin.ui.system

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SystemRepository {
    suspend fun getArticleCategories() = RetrofitClient.apiService.getArticleCategories().apiData()
}