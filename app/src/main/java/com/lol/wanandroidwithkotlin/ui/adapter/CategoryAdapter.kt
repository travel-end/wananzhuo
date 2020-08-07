package com.lol.wanandroidwithkotlin.ui.adapter

import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.updateLayoutParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.ext.htmlToSpanned
import com.lol.wanandroidwithkotlin.ext.toIntPx
import com.lol.wanandroidwithkotlin.model.bean.Category
import kotlinx.android.synthetic.main.item_category_sub.view.*

/**
 * Created by xiaojianjun on 2019-11-14.
 */
class CategoryAdapter(layoutResId: Int = R.layout.item_category_sub) :
    BaseQuickAdapter<Category, BaseViewHolder>(layoutResId) {

    private var checkedPosition = 0
    var onCheckedListener: ((position: Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.itemView.run {
            ctvCategory.text = item.name.htmlToSpanned()
            ctvCategory.isChecked = checkedPosition == helper.adapterPosition
            setOnClickListener {
                val position = helper.adapterPosition
                check(position)
                onCheckedListener?.invoke(position)
            }
            updateLayoutParams<MarginLayoutParams> {
                marginStart = if (helper.adapterPosition == 0) 8f.toIntPx() else 0f.toIntPx()
            }
        }
    }

    fun check(position: Int) {
        checkedPosition = position
        notifyDataSetChanged()
    }

}