package com.lol.wanandroidwithkotlin.ui.register

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class RegisterViewModel:BaseViewModel() {
    private val registerRepository by lazy { RegisterRepository() }
    val submitting = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun register(account: String, password: String, confirmPassword: String) {
        submitting.value = true
        launch(
            block = {
                val userInfo = registerRepository.register(account, password, confirmPassword)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                registerResult.value = true
                submitting.value = false
            },
            error = {
                registerResult.value = false
                submitting.value = false
            }
        )
    }
}