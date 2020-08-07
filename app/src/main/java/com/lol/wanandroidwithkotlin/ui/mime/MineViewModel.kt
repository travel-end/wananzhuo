package com.lol.wanandroidwithkotlin.ui.mime

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.UserInfo

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class MineViewModel : BaseViewModel() {

//    private val mineRespository by lazy { MineRespository() }

    val userInfo = MutableLiveData<UserInfo?>()
    val isLogin = MutableLiveData<Boolean>()

    fun getUserInfo() {
        isLogin.value = userRepository.isLogin()
        userInfo.value = userRepository.getUserInfo()
    }
}