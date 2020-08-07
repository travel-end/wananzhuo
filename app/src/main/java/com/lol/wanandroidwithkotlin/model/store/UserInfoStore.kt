package com.lol.wanandroidwithkotlin.model.store

import com.google.gson.Gson
import com.lol.wanandroidwithkotlin.MyApp
import com.lol.wanandroidwithkotlin.model.UserInfo
import com.lol.wanandroidwithkotlin.util.clearSpValue
import com.lol.wanandroidwithkotlin.util.getSpValue
import com.lol.wanandroidwithkotlin.util.putSpValue

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
object UserInfoStore {
    private const val SP_USER_INFO = "sp_user_info"
    private const val KEY_USER_INFO = "user_info"


    private val mGson by lazy { Gson() }

    fun isLogin() :Boolean {
        val userInfoStr = getSpValue(SP_USER_INFO,MyApp.instance, KEY_USER_INFO,"")
        return userInfoStr.isNotEmpty()
    }

    fun getUserInfo():UserInfo? {
        val userInfoStr = getSpValue(SP_USER_INFO, MyApp.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            mGson.fromJson(userInfoStr,UserInfo::class.java)
        } else {
            null
        }
    }

    fun setUserInfo(userInfo: UserInfo) =
        putSpValue(SP_USER_INFO,MyApp.instance, KEY_USER_INFO, mGson.toJson(userInfo))

    fun clearUserInfo() {
        clearSpValue(SP_USER_INFO,MyApp.instance)
    }

}