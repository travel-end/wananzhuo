package com.lol.wanandroidwithkotlin.ui.system.pager

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SystemPagerRepository  {
    suspend fun getArticleListByCid(page: Int, cid: Int) =
        RetrofitClient.apiService.getArticleListByCid(page, cid).apiData()
}