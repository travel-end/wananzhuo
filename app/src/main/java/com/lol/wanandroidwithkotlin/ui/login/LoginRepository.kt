package com.lol.wanandroidwithkotlin.ui.login

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class LoginRepository {
    suspend fun login(username:String,password:String) = RetrofitClient.apiService.login(username,password).apiData()

}