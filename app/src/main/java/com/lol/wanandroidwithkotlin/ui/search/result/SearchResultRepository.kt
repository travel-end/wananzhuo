package com.lol.wanandroidwithkotlin.ui.search.result

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SearchResultRepository {
    suspend fun search(keywords: String, page: Int) =
        RetrofitClient.apiService.search(keywords, page).apiData()
}