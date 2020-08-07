package com.lol.wanandroidwithkotlin.ui.point

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class MinePointsRespository {
    suspend fun getMyPoints() = RetrofitClient.apiService.getPoints().apiData()
    suspend fun getPointsRecord(page:Int)=RetrofitClient.apiService.getPointsRecord(page).apiData()
}