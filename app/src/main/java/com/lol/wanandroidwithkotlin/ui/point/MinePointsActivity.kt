package com.lol.wanandroidwithkotlin.ui.point

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ui.adapter.MinePointsAdapter
import com.lol.wanandroidwithkotlin.ui.point.rank.PointsRankActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_mine_points.*
import kotlinx.android.synthetic.main.header_mine_points.view.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class MinePointsActivity:BaseVmActivity<MinePointsViewModel>() {
    override fun viewModelClass()=MinePointsViewModel::class.java
    override fun layoutRes()=R.layout.activity_mine_points
    private lateinit var minePointsAdapter:MinePointsAdapter
    private lateinit var mHeaderView:View

    override fun initView() {
        super.initView()
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.header_mine_points,null)
        minePointsAdapter = MinePointsAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({mViewModel.loadMoreRecord()},recyclerView)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refresh() }
        }
        ivBack.setOnClickListener { ActivityManager.finish(MinePointsActivity::class.java) }
        ivRank.setOnClickListener { ActivityManager.start(PointsRankActivity::class.java) }
        btnReload.setOnClickListener { mViewModel.refresh() }
    }

    override fun initData() {
        super.initData()
        mViewModel.refresh()
    }
    override fun observe() {
        super.observe()
        mViewModel.run {
            totalPoints.observe(this@MinePointsActivity, Observer {
                if (minePointsAdapter.headerLayoutCount == 0) {
                    minePointsAdapter.setHeaderView(mHeaderView)
                }
                mHeaderView.tvTotalPoints.text = it.coinCount.toString()
                mHeaderView.tvLevelRank.text = getString(R.string.level_rank, it.level, it.rank)

            })
            pointsList.observe(this@MinePointsActivity, Observer {
                minePointsAdapter.setNewData(it)
            })
            refreshStatus.observe(this@MinePointsActivity, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(this@MinePointsActivity, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> minePointsAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> minePointsAdapter.loadMoreFail()
                    LoadMoreStatus.END -> minePointsAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(this@MinePointsActivity, Observer {
                reloadView.isVisible = it
            })
        }
    }
}