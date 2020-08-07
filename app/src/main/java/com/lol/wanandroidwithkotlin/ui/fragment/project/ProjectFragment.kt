package com.lol.wanandroidwithkotlin.ui.fragment.project

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.common.loadmore.CommonLoadMoreView
import com.lol.wanandroidwithkotlin.common.loadmore.LoadMoreStatus
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.ui.adapter.ArticleAdapter
import com.lol.wanandroidwithkotlin.ui.adapter.CategoryAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.include_reload.view.*

class ProjectFragment : BaseVmFragment<ProjectViewModel>() {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private lateinit var mAdapter: ArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter

    override fun layoutRes() = R.layout.fragment_project

    override fun viewModelClass() = ProjectViewModel::class.java

    override fun initView() {
//        loge("ProjectFragment")
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshProjectList() }
        }
        mCategoryAdapter = CategoryAdapter(R.layout.item_category_sub)
            .apply {
                bindToRecyclerView(rvCategory)
                onCheckedListener = {
                    mViewModel.refreshProjectList(it)
                }
            }
        mAdapter = ArticleAdapter(R.layout.item_article).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreProjectList()
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
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
        }
        reloadListView.btnReload.setOnClickListener {
            mViewModel.refreshProjectList()
        }
        reloadView.btnReload.setOnClickListener {
            mViewModel.getProjectCategory()
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
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapter.loadMoreFail()
                    LoadMoreStatus.END -> mAdapter.loadMoreEnd()
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
        mViewModel.getProjectCategory()
    }
}
