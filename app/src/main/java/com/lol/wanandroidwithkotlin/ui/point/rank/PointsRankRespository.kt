package com.lol.wanandroidwithkotlin.ui.point.rank

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class PointsRankRespository {
    suspend fun getPointsRank(page: Int) =
        RetrofitClient.apiService.getPointsRank(page).apiData()
}