package com.lol.wanandroidwithkotlin.common

import com.lol.wanandroidwithkotlin.model.UserInfo
import com.lol.wanandroidwithkotlin.model.store.UserInfoStore

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
class UserRepository {
    fun updateUserInfo(userInfo: UserInfo) = UserInfoStore.setUserInfo(userInfo)

    fun isLogin() = UserInfoStore.isLogin()

    fun getUserInfo() = UserInfoStore.getUserInfo()

    fun clearLoginState() {
        UserInfoStore.clearUserInfo()
    }
}