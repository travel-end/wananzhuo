package com.lol.wanandroidwithkotlin.ui.fragment.plaza

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.ui.adapter.SimpleArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_plaza.*
import kotlinx.android.synthetic.main.include_reload.*

class PlazaFragment : BaseVmFragment<PlazaViewModel>(){

    companion object {
        fun newInstance() = PlazaFragment()
    }

    private lateinit var mAdapterSimple: SimpleArticleAdapter

    override fun layoutRes() = R.layout.fragment_plaza

    override fun viewModelClass() = PlazaViewModel::class.java

    override fun initView() {
//        loge("PlazaFragment")
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList() }
        }
        mAdapterSimple = SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapterSimple.data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = mAdapterSimple.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
        }
        btnReload.setOnClickListener {
            mViewModel.refreshArticleList()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapterSimple.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapterSimple.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapterSimple.loadMoreFail()
                    LoadMoreStatus.END -> mAdapterSimple.loadMoreEnd()
                    else -> return@Observer
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, viewLifecycleOwner) {
            mViewModel.updateItemCollectState(it)
        }
    }

    override fun lazeLoadData() {
        mViewModel.refreshArticleList()
    }
}
