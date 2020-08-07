package com.lol.wanandroidwithkotlin.ui.fragment.latest

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient


/**
 * Created by xiaojianjun on 2019-09-18.
 */
class LatestRepository {
    suspend fun getProjectList(page: Int) = RetrofitClient.apiService.getProjectList(page).apiData()
}