package com.lol.wanandroidwithkotlin.ui.point.rank

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.model.bean.PointRank

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class PointsRankViewModel:BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 1
    }

    private val pointsRankRespository by lazy { PointsRankRespository() }

    val pointsRank = MutableLiveData<MutableList<PointRank>>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshData() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val pagination = pointsRankRespository.getPointsRank(INITIAL_PAGE)
                page = pagination.curPage
                pointsRank.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreData() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = pointsRankRespository.getPointsRank(page + 1)
                page = pagination.curPage
                pointsRank.value?.addAll(pagination.datas)
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = { loadMoreStatus.value = LoadMoreStatus.ERROR }
        )
    }
}