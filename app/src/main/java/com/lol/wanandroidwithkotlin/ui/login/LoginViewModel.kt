package com.lol.wanandroidwithkotlin.ui.login

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class LoginViewModel:BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }

    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    fun login(account:String, password:String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = loginRepository.login(account,password)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED,true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value =false
                loginResult.value = false
            }
        )
    }
}