package com.lol.wanandroidwithkotlin.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.PointRank
import kotlinx.android.synthetic.main.item_points_rank.view.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
@SuppressLint("SetTextI18n")
class PointsRankAdapter : BaseQuickAdapter<PointRank, BaseViewHolder>(R.layout.item_points_rank) {
    override fun convert(helper: BaseViewHolder, item: PointRank) {
        helper.itemView.run {
            tvNo.text = "${helper.adapterPosition + 1}"
            tvName.text = item.username
            tvPoints.text = item.coinCount.toString()
        }
    }
}