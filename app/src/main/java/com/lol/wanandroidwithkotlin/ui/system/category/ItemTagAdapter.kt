package com.lol.wanandroidwithkotlin.ui.system.category

import android.view.LayoutInflater
import android.view.View
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.Category
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_system_category_tag.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class ItemTagAdapter(private val categoryList: List<Category>) : TagAdapter<Category>(categoryList) {
    override fun getView(parent: FlowLayout?, position: Int, category: Category?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_system_category_tag, parent, false)
            .apply {
                tvTag.text = categoryList[position].name
            }
    }

    override fun setSelected(position: Int, t: Category?): Boolean {
        return super.setSelected(position, t)
    }
}