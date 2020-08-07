package com.lol.wanandroidwithkotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.HotWord
import kotlinx.android.synthetic.main.item_hot_word.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class HotWordsAdapter(layoutResId: Int = R.layout.item_hot_word) :
    BaseQuickAdapter<HotWord, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: HotWord) {
        helper.itemView.tvName.text = item.name
    }


}