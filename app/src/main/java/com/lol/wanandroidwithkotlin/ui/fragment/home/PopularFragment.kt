package com.lol.wanandroidwithkotlin.ui.fragment.home

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ui.adapter.ArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class PopularFragment:BaseVmFragment<PopularViewModel>() {
    override fun viewModelClass()=PopularViewModel::class.java
    override fun layoutRes()=R.layout.fragment_popular
    private lateinit var mAdapter:ArticleAdapter
    companion object {
        fun newInstance() = PopularFragment()
    }

    override fun initView() {
//        loge("PopularFragment")

        /**
         * run:在函数块内可以通过this指代该对象，返回值为函数块的最后一行
         * 或者指定return表达式
         */
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary) // refresh icon color
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary) //refresh icon bgColor
            setOnRefreshListener {
                mViewModel.refreshArticleList()
            }
        }
        /**
         * apply:调用某对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象
         *
         * 如果这里的apply改成run，返回的对象不符合要求了
         */
        mAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            },recyclerView)
            setOnItemClickListener { adapter, view, position ->
                val article = mAdapter.data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { adapter, view, position ->
                val article = mAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
            btnReload.setOnClickListener {
                mViewModel.refreshArticleList()
            }
        }

    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it) {
                    LoadMoreStatus.COMPLETED->{
                        mAdapter.loadMoreComplete()
                    }
                    LoadMoreStatus.ERROR -> {
                        mAdapter.loadMoreFail()
                    }
                    LoadMoreStatus.END ->{
                        mAdapter.loadMoreEnd()
                    }
                    else ->{
                        return@Observer
                    }
                }
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
            Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner) {
                mViewModel.updateListCollectState()
            }
            Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,viewLifecycleOwner) {
                mViewModel.updateItemCollectState(it)
            }
        }
    }

    override fun lazeLoadData() {
        mViewModel.refreshArticleList()
    }

}