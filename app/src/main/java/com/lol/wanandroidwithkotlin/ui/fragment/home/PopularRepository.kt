package com.lol.wanandroidwithkotlin.ui.fragment.home

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
class PopularRepository {
    suspend fun getTopArticleList()=RetrofitClient.apiService.getTopArticleList().apiData()
    suspend fun getArticleList(page:Int) = RetrofitClient.apiService.getArticleList(page).apiData()
}