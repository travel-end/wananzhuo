package com.lol.wanandroidwithkotlin.ui.navigation

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.bean.Navigation

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class NavigationViewModel:BaseViewModel() {

    private val navigationRepository by lazy { NavigationRepository() }
    val navigations = MutableLiveData<List<Navigation>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    fun getNavigations() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                navigations.value = navigationRepository.getNavigations()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = navigations.value.isNullOrEmpty()
            }
        )
    }
}