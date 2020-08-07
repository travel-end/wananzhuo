package com.lol.wanandroidwithkotlin.ui.shared

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ui.adapter.ArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.ui.share.ShareActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.activity_shared.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SharedActivity : BaseVmActivity<SharedViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    override fun layoutRes() = R.layout.activity_shared

    override fun viewModelClass() = SharedViewModel::class.java

    override fun initView() {
        mAdapter = ArticleAdapter().apply {
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
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
            setOnItemLongClickListener { _, _, position ->
                AlertDialog.Builder(this@SharedActivity)
                    .setMessage(R.string.confirm_delete_shared)
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.confirm) { _, _ ->
                        mViewModel.deleteShared(data[position].id)
                        mAdapter.remove(position)
                        this@SharedActivity.emptyView.isVisible = data.isEmpty()
                    }.show()
                true
            }
            setOnLoadMoreListener({ mViewModel.loadMoreArticleList() }, recyclerView)
        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList() }
        }
        btnReload.setOnClickListener {
            mViewModel.refreshArticleList()
        }
        ivAdd.setOnClickListener { ActivityManager.start(ShareActivity::class.java) }
        ivBack.setOnClickListener { ActivityManager.finish(SharedActivity::class.java) }
    }

    override fun initData() {
        mViewModel.refreshArticleList()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(this@SharedActivity, Observer {
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(this@SharedActivity, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            emptyStatus.observe(this@SharedActivity, Observer {
                emptyView.isVisible = it
            })
            loadMoreStatus.observe(this@SharedActivity, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(this@SharedActivity, Observer {
                reloadView.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, this) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, this) {
            mViewModel.updateItemCollectState(it)
        }
    }
}
