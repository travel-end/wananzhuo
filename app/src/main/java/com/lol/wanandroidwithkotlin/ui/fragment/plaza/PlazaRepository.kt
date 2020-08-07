package com.lol.wanandroidwithkotlin.ui.fragment.plaza

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class PlazaRepository {
    suspend fun getUserArticleList(page:Int) =
        RetrofitClient.apiService.getUserArticleList(page).apiData()
}