package com.lol.wanandroidwithkotlin.ui.fragment.wechat

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.ui.adapter.CategoryAdapter
import com.lol.wanandroidwithkotlin.ui.adapter.SimpleArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_wechat.*
import kotlinx.android.synthetic.main.include_reload.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class WechatFragment : BaseVmFragment<WechatViewModel>(){

    companion object {
        fun newInstance() = WechatFragment()
    }

    private lateinit var mAdapterSimple: SimpleArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun layoutRes() = R.layout.fragment_wechat

    override fun viewModelClass() = WechatViewModel::class.java

    override fun initView() {
//        loge("WechatFragment")
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshWechatArticleList() }
        }
        mCategoryAdapter = CategoryAdapter(R.layout.item_category_sub)
            .apply {
                bindToRecyclerView(rvCategory)
                onCheckedListener = {
                    mViewModel.refreshWechatArticleList(it)
                }
            }
        mAdapterSimple = SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreWechatArticleList()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapterSimple.data[position]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAM_ARTICLE to article)
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
        reloadListView.btnReload.setOnClickListener {
            mViewModel.refreshWechatArticleList()
        }
        reloadView.btnReload.setOnClickListener {
            mViewModel.getWechatCategory()
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                rvCategory.isGone = it.isEmpty()
                mCategoryAdapter.setNewData(it)
            })
            checkedCategory.observe(viewLifecycleOwner, Observer {
                mCategoryAdapter.check(it)
            })
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
            reloadListStatus.observe(viewLifecycleOwner, Observer {
                reloadListView.isVisible = it
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
        mViewModel.getWechatCategory()
    }

}