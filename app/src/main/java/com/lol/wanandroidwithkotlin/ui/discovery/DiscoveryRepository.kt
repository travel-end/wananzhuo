package com.lol.wanandroidwithkotlin.ui.discovery

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class DiscoveryRepository {
    suspend fun getBanners() = RetrofitClient.apiService.getBanners().apiData()
    suspend fun getHotWords() = RetrofitClient.apiService.getHotWords().apiData()
    suspend fun getFrequentlyWebsites() = RetrofitClient.apiService.getFrequentlyWebsites().apiData()
}