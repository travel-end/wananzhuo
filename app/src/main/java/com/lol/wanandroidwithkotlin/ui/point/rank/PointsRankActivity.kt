package com.lol.wanandroidwithkotlin.ui.point.rank

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ui.adapter.PointsRankAdapter
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_points_rank.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class PointsRankActivity : BaseVmActivity<PointsRankViewModel>() {

    private lateinit var mAdapter: PointsRankAdapter

    override fun layoutRes() = R.layout.activity_points_rank

    override fun viewModelClass() = PointsRankViewModel::class.java

    override fun initView() {
        mAdapter = PointsRankAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({ mViewModel.loadMoreData() }, recyclerView)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshData() }
        }
        ivBack.setOnClickListener { ActivityManager.finish(PointsRankActivity::class.java) }
        tvTitle.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        btnReload.setOnClickListener { mViewModel.refreshData() }
    }

    override fun initData() {
        mViewModel.refreshData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            pointsRank.observe(this@PointsRankActivity, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(this@PointsRankActivity, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(this@PointsRankActivity, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(this@PointsRankActivity, Observer {
                reloadView.isVisible = it
            })
        }
    }
}
