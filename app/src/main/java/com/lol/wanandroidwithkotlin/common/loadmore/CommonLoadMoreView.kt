package com.lol.wanandroidwithkotlin.common.loadmore

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.lol.wanandroidwithkotlin.R

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class CommonLoadMoreView: LoadMoreView() {
    override fun getLayoutId()=R.layout.view_load_more_common

    override fun getLoadingViewId() = R.id.load_more_loading_view

    override fun getLoadEndViewId()= R.id.load_more_load_end_view
    override fun getLoadFailViewId() = R.id.load_more_load_fail_view
}