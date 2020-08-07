package com.lol.wanandroidwithkotlin.ui.navigation

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lol.wanandroidwithkotlin.MainActivity
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.ui.adapter.NavigationAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class NavigationFragment:BaseVmFragment<NavigationViewModel>() {
    private lateinit var mAdapter: NavigationAdapter
    private var currentPosition = 0
    override fun viewModelClass()=NavigationViewModel::class.java
    override fun layoutRes()= R.layout.fragment_navigation
    companion object {
        fun newInstance() = NavigationFragment()
    }
    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getNavigations() }
        }
        mAdapter = NavigationAdapter(R.layout.item_navigation).apply {
            bindToRecyclerView(recyclerView)
            onItemTagClickListener = {
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to Article(title = it.title, link = it.link))
                )
            }
        }
        btnReload.setOnClickListener {
            mViewModel.getNavigations()
        }
        recyclerView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (activity is MainActivity && scrollY != oldScrollY) {
                (activity as MainActivity).animateBottomNavigationView(scrollY < oldScrollY)
            }
            if (scrollY < oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
            val lm = recyclerView.layoutManager as LinearLayoutManager
            val nextView = lm.findViewByPosition(currentPosition + 1)
            if (nextView != null) {
                tvFloatTitle.y = if (nextView.top < tvFloatTitle.measuredHeight) {
                    (nextView.top - tvFloatTitle.measuredHeight).toFloat()
                } else {
                    0f
                }
            }
            currentPosition = lm.findFirstVisibleItemPosition()
            if (scrollY > oldScrollY) {
                tvFloatTitle.text = mAdapter.data[currentPosition].name
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            navigations.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone = it.isEmpty()
                tvFloatTitle.text = it[0].name
                mAdapter.setNewData(it)
            })
            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun initData() {
        mViewModel.getNavigations()
    }

//    override fun scrollToTop() {
//        recyclerView?.smoothScrollToPosition(0)
//    }
}