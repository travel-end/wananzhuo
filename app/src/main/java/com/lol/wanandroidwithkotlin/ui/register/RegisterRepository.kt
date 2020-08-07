package com.lol.wanandroidwithkotlin.ui.register

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class RegisterRepository {
    suspend fun register(username:String, password:String,repassword:String) =
        RetrofitClient.apiService.register(username,password,repassword).apiData()
}