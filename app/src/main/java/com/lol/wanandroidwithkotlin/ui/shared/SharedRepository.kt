package com.lol.wanandroidwithkotlin.ui.shared

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SharedRepository {
    suspend fun getSharedArticleList(page: Int) =
        RetrofitClient.apiService.getSharedArticleList(page).apiData()

    suspend fun deleteShared(id: Int) = RetrofitClient.apiService.deleteShare(id).apiData()
}