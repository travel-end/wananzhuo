package com.lol.wanandroidwithkotlin.ui.navigation

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class NavigationRepository {
    suspend fun getNavigations() = RetrofitClient.apiService.getNavigations().apiData()
}