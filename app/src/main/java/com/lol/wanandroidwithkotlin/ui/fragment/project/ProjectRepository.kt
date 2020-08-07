package com.lol.wanandroidwithkotlin.ui.fragment.project

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class ProjectRepository {
    suspend fun getProjectCategories() = RetrofitClient.apiService.getProjectCategories().apiData()
    suspend fun getProjectListByCid(page: Int, cid: Int) = RetrofitClient.apiService.getProjectListByCid(page, cid).apiData()
}