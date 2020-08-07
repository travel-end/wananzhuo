package com.lol.wanandroidwithkotlin.ui.fragment.wechat

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class WechatRepository {
    suspend fun getWechatCategories() = RetrofitClient.apiService.getWechatCategories().apiData()
    suspend fun getWechatArticleList(page:Int,id:Int) = RetrofitClient.apiService.getWechatArticleList(page,id).apiData()
}