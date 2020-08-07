package com.lol.wanandroidwithkotlin.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.lol.wanandroidwithkotlin.MainActivity
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseFragment
import com.lol.wanandroidwithkotlin.common.SimpleFragmentPageAdapter
import com.lol.wanandroidwithkotlin.ui.fragment.home.PopularFragment
import com.lol.wanandroidwithkotlin.ui.fragment.latest.LatestFragment
import com.lol.wanandroidwithkotlin.ui.fragment.plaza.PlazaFragment
import com.lol.wanandroidwithkotlin.ui.fragment.project.ProjectFragment
import com.lol.wanandroidwithkotlin.ui.fragment.wechat.WechatFragment
import com.lol.wanandroidwithkotlin.ui.search.SearchActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
class HomeFragment:BaseFragment() {

    private lateinit var fragments :List<Fragment>
    private var currentOffset = 0
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutRes()= R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments = listOf(
            PopularFragment.newInstance(),
            LatestFragment.newInstance(),
            PlazaFragment.newInstance(),
            ProjectFragment.newInstance(),
            WechatFragment.newInstance()

        )
        val titles = listOf<CharSequence>(
            getString(R.string.popular_articles),
            getString(R.string.latest_project),
            getString(R.string.plaza),
            getString(R.string.project_category),
            getString(R.string.wechat_public)
        )
        viewPager.adapter= SimpleFragmentPageAdapter(childFragmentManager,fragments,titles)
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.setupWithViewPager(viewPager)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{_, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }

        })
        llSearch.setOnClickListener { ActivityManager.start(SearchActivity::class.java) }
    }

}