package com.lol.wanandroidwithkotlin.ui.fragment.wechat

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.common.CollectRepository
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Category
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class WechatViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_CHECKED = 0
        const val INITIAL_PAGE = 1
    }

    private val wechatRepository by lazy { WechatRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val checkedCategory: MutableLiveData<Int> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val reloadListStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE


    fun getWechatCategory() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val categoryList = wechatRepository.getWechatCategories()
                val checkedPosition = INITIAL_CHECKED
                val id = categoryList[checkedPosition].id
                val pagination = wechatRepository.getWechatArticleList(INITIAL_PAGE, id)
                page = pagination.curPage

                categories.value = categoryList
                checkedCategory.value = checkedPosition
                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

    fun refreshWechatArticleList(checkedPosition: Int = checkedCategory.value ?: INITIAL_CHECKED) {
        refreshStatus.value = true
        reloadListStatus.value = false
        if (checkedPosition != checkedCategory.value) {
            articleList.value = mutableListOf()
            checkedCategory.value = checkedPosition
        }
        launch(
            block = {
                val categoryList = categories.value ?: return@launch
                val id = categoryList[checkedPosition].id
                val pagination = wechatRepository.getWechatArticleList(INITIAL_PAGE, id)
                page = pagination.curPage

                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadListStatus.value = articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreWechatArticleList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val categoryList = categories.value ?: return@launch
                val checkedPosition = checkedCategory.value ?: return@launch
                val id = categoryList[checkedPosition].id
                val pagination = wechatRepository.getWechatArticleList(page + 1, id)
                page = pagination.curPage

                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList

                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
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

    /**
     * 更新列表收藏状态
     */
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

    /**
     * 更新Item的收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}
