package com.lol.wanandroidwithkotlin.ui.system

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.bean.Category

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SystemViewModel:BaseViewModel() {
    private val systemRepository by lazy { SystemRepository() }
    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val loadingStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()


    fun getArticleCategory() {
        loadingStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                categories.value = systemRepository.getArticleCategories()
                loadingStatus.value = false
            },
            error = {
                loadingStatus.value = false
                reloadStatus.value = true
            }
        )
    }
}