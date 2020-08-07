package com.lol.wanandroidwithkotlin.ui.collection

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ui.adapter.ArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.include_reload.*

class CollectionActivity : BaseVmActivity<CollectionViewModel>() {

    private lateinit var mAdater: ArticleAdapter

    override fun layoutRes() = R.layout.activity_collection

    override fun viewModelClass() = CollectionViewModel::class.java
    
    override fun initView() {
        mAdater = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect) {
                    mViewModel.uncollect(article.originId)
                    removeItem(position)
                }
            }
            setOnLoadMoreListener({ mViewModel.loadMore() }, recyclerView)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refresh() }
        }
        btnReload.setOnClickListener {
            mViewModel.refresh()
        }
        ivBack.setOnClickListener { ActivityManager.finish(CollectionActivity::class.java) }
    }

    override fun initData() {
        mViewModel.refresh()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(this@CollectionActivity, Observer {
                mAdater.setNewData(it)
            })
            refreshStatus.observe(this@CollectionActivity, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            emptyStatus.observe(this@CollectionActivity, Observer {
                emptyView.isVisible = it
            })
            loadMoreStatus.observe(this@CollectionActivity, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdater.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdater.loadMoreFail()
                    LoadMoreStatus.END -> mAdater.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(this@CollectionActivity, Observer {
                reloadView.isVisible = it
            })
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, this) { (id, collect) ->
            if (collect) {
                mViewModel.refresh()
            } else {
                val position = mAdater.data.indexOfFirst { it.originId == id }
                if (position != -1) {
                    removeItem(position)
                }
            }
        }
    }

    private fun removeItem(position: Int) {
        mAdater.remove(position)
        emptyView.isVisible = mAdater.data.isEmpty()
    }

}
