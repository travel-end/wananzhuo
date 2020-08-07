package com.lol.wanandroidwithkotlin.ui.point

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.model.bean.PointRank
import com.lol.wanandroidwithkotlin.model.bean.PointRecord

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class MinePointsViewModel :BaseViewModel() {

    companion object{
        const val INITIAL_PAGE  = 1
    }
    private val minePointsRespository by lazy { MinePointsRespository() }

    val totalPoints = MutableLiveData<PointRank>()
    val pointsList = MutableLiveData<MutableList<PointRecord>>()

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    var page = INITIAL_PAGE

    fun refresh() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val points = minePointsRespository.getMyPoints()
                val pagination = minePointsRespository.getPointsRecord(INITIAL_PAGE)
                page = pagination.curPage
                totalPoints.value = points
                pointsList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }
    fun loadMoreRecord() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = minePointsRespository.getPointsRecord(page + 1)
                page = pagination.curPage
                pointsList.value?.addAll(pagination.datas)
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