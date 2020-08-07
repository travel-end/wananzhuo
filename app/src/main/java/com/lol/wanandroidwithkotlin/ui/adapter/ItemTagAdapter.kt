package com.lol.wanandroidwithkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class ItemTagAdapter(private val articles:List<Article>):TagAdapter<Article>(articles) {
    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {

        return LayoutInflater.from(parent?.context).inflate(R.layout.item_nav_tag,parent,false).apply {
            tvTag.text = articles[position].title
        }
    }
}