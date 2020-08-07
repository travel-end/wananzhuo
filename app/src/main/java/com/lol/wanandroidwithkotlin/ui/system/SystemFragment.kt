package com.lol.wanandroidwithkotlin.ui.system

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.lol.wanandroidwithkotlin.MainActivity
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.common.SimpleFragmentPageAdapter
import com.lol.wanandroidwithkotlin.model.bean.Category
import com.lol.wanandroidwithkotlin.ui.system.category.SystemCategoryFragment
import com.lol.wanandroidwithkotlin.ui.system.pager.SystemPagerFragment
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.include_reload.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SystemFragment:BaseVmFragment<SystemViewModel>() {
    private var currentOffset = 0
    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<SystemPagerFragment>()
    private var categoryFragment: SystemCategoryFragment? = null

    override fun viewModelClass()=SystemViewModel::class.java
    override fun layoutRes()=R.layout.fragment_system
    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun initView() {
        reloadView.btnReload.setOnClickListener {
            mViewModel.getArticleCategory()
        }
        ivFilter.setOnClickListener {
            categoryFragment?.show(childFragmentManager)
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                ivFilter.visibility = View.VISIBLE
                tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                setup(it)
                categoryFragment = SystemCategoryFragment.newInstance(ArrayList(it))
            })
            loadingStatus.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible = it
            })
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible = it
            })
        }
    }

    override fun initData() {
        mViewModel.getArticleCategory()
    }

    private fun setup(categories: MutableList<Category>) {
        titles.clear()
        fragments.clear()
        categories.forEach {
            titles.add(it.name)
            fragments.add(SystemPagerFragment.newInstance(it.children))
        }
        viewPager.adapter = SimpleFragmentPageAdapter(childFragmentManager, fragments, titles)
        viewPager.offscreenPageLimit = titles.size
        tabLayout.setupWithViewPager(viewPager)
    }

//    override fun scrollToTop() {
//        if (fragments.isEmpty() || viewPager == null) return
//        fragments[viewPager.currentItem].scrollToTop()
//    }

    fun getCurrentChecked(): Pair<Int, Int> {
        if (fragments.isEmpty() || viewPager == null) return 0 to 0
        val first = viewPager.currentItem
        val second = fragments[viewPager.currentItem].checkedPosition
        return first to second
    }

    fun check(position: Pair<Int, Int>) {
        if (fragments.isEmpty() || viewPager == null) return
        viewPager.currentItem = position.first
        fragments[position.first].check(position.second)
    }
}