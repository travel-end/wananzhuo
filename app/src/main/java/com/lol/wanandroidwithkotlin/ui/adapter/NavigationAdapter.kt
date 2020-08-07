package com.lol.wanandroidwithkotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Navigation
import kotlinx.android.synthetic.main.item_navigation.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class NavigationAdapter(layoutResId: Int = R.layout.item_navigation) :
    BaseQuickAdapter<Navigation, BaseViewHolder>(layoutResId) {
    var onItemTagClickListener: ((article: Article) -> Unit)? = null
    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.itemView.run {
            title.text = item.name
            tagFlawLayout.adapter = ItemTagAdapter(item.articles)
            tagFlawLayout.setOnTagClickListener { view, position, parent ->
                onItemTagClickListener?.invoke(item.articles[position])
                true
            }
        }

    }

}