package com.lol.wanandroidwithkotlin.ui.share

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.UserInfo

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class ShareViewModel : BaseViewModel() {

    private val shareRepository by lazy { ShareRepository() }
    val userInfo = MutableLiveData<UserInfo>()
    val submitting = MutableLiveData<Boolean>()
    val shareResult = MutableLiveData<Boolean>()

    fun getUserInfo() {
        userInfo.value = userRepository.getUserInfo()
    }

    fun shareArticle(title: String, link: String) {
        submitting.value = true
        launch(
            block = {
                shareRepository.shareArticle(title, link)
                shareResult.value = true
                submitting.value = false
            },
            error = {
                shareResult.value = false
                submitting.value = false
            }
        )
    }

}