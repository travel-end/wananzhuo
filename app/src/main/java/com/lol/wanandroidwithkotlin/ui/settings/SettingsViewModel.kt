package com.lol.wanandroidwithkotlin.ui.settings

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SettingsViewModel :BaseViewModel(){

    val mIsLogin = MutableLiveData<Boolean>()
    fun getLoginStatus() {
        mIsLogin.value = userRepository.isLogin()
    }

    fun logout() {
        userRepository.clearLoginState()
        Bus.post(USER_LOGIN_STATE_CHANGED, false)
    }
}