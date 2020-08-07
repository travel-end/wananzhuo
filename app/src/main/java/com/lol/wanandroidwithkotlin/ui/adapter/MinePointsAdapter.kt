package com.lol.wanandroidwithkotlin.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.ext.toDateTime
import com.lol.wanandroidwithkotlin.model.bean.PointRecord
import kotlinx.android.synthetic.main.item_mine_points.view.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class MinePointsAdapter:BaseQuickAdapter<PointRecord,BaseViewHolder>(R.layout.item_mine_points) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: PointRecord) {

        helper.itemView.run {
            tvReason.text = item.reason
            tvTime.text = item.date.toDateTime("YYYY-MM-dd HH:mm:ss")
            tvPoint.text = "+${item.coinCount}"
        }
    }
}