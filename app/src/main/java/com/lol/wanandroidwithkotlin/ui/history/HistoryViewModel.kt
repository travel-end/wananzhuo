package com.lol.wanandroidwithkotlin.ui.history

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.common.CollectRepository
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class HistoryViewModel : BaseViewModel() {


    private val historyRepository by lazy { HistoryRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>()
    val emptyStatus = MutableLiveData<Boolean>()


    fun getData() {
        emptyStatus.value = false
        launch(
            block = {
                val readHistory = historyRepository.getReadHistory()
                val collectIds = userRepository.getUserInfo()?.collectIds ?: emptyList<Int>()
                // 更新收藏状态
                readHistory.forEach { it.collect = collectIds.contains(it.id) }

                articleList.value = readHistory.toMutableList()
                emptyStatus.value = readHistory.isEmpty()
            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }

    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }

    fun deleteHistory(article: Article) {
        launch(
            block = { historyRepository.deleteHistory(article) }
        )
    }
}