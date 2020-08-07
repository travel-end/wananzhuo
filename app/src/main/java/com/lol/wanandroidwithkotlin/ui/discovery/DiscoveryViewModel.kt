package com.lol.wanandroidwithkotlin.ui.discovery

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.bean.Banner
import com.lol.wanandroidwithkotlin.model.bean.Frequently
import com.lol.wanandroidwithkotlin.model.bean.HotWord

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class DiscoveryViewModel : BaseViewModel() {

    private val dicoveryRepository by lazy { DiscoveryRepository() }
    val banners = MutableLiveData<List<Banner>>()
    val hotWords = MutableLiveData<List<HotWord>>()
    val frequentlyList = MutableLiveData<List<Frequently>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                banners.value = dicoveryRepository.getBanners()
                hotWords.value = dicoveryRepository.getHotWords()
                frequentlyList.value = dicoveryRepository.getFrequentlyWebsites()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}