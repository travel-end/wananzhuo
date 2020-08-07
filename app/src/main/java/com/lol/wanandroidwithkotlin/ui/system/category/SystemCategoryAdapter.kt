package com.lol.wanandroidwithkotlin.ui.system.category

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.ext.htmlToSpanned
import com.lol.wanandroidwithkotlin.model.bean.Category
import kotlinx.android.synthetic.main.item_system_category.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class SystemCategoryAdapter(
    layoutResId: Int = R.layout.item_system_category,
    categoryList: List<Category>,
    var checked: Pair<Int, Int>
) : BaseQuickAdapter<Category, BaseViewHolder>(layoutResId, categoryList) {

    var onCheckedListener: ((checked: Pair<Int, Int>) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.itemView.run {
            title.text = item.name.htmlToSpanned()
            tagFlowLayout.adapter = ItemTagAdapter(item.children)
            if (checked.first == helper.adapterPosition) {
                tagFlowLayout.adapter.setSelectedList(checked.second)
            }
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                checked = helper.adapterPosition to position
                notifyDataSetChanged()
                tagFlowLayout.postDelayed({
                    onCheckedListener?.invoke(checked)
                }, 300)
                true
            }
        }
    }
}