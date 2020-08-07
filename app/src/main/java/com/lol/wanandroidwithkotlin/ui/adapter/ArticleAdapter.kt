package com.lol.wanandroidwithkotlin.ui.adapter

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.ext.htmlToSpanned
import com.lol.wanandroidwithkotlin.model.bean.Article
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * @Create by Jingui on 2020/6/3
 * @Describe:
 * @email: m15008497107@163.com
 */
class ArticleAdapter(layoutResId:Int = R.layout.item_article): BaseQuickAdapter<Article, BaseViewHolder> (layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Article) {

        helper.run {
            itemView.run {
                tv_author.text = when {
                    !item.author.isNullOrEmpty() ->{
                        item.author
                    }
                    !item.shareUser.isNullOrEmpty() ->{
                        item.shareUser
                    }
                    else->{
                        context.getString(R.string.anonymous)
                    }
                }
                tv_top.isVisible = item.top
                tv_fresh.isVisible = item.fresh && !item.top
                tv_tag.visibility = if (item.tags.isNotEmpty()) {
                    tv_tag.text = item.tags[0].name
                    View.VISIBLE
                } else {
                    View.GONE
                }
                tv_chapter.text = when {
                    !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        "${item.superChapterName.htmlToSpanned()}/${item.chapterName.htmlToSpanned()}"
                    item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        item.chapterName.htmlToSpanned()
                    !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                        item.superChapterName.htmlToSpanned()
                    else -> ""
                }
                tv_title.text = item.title.htmlToSpanned()
                tv_desc.text = item.desc.htmlToSpanned()
                tv_desc.isGone = item.desc.isNullOrEmpty()
                tv_time.text = item.niceDate
                iv_collect.isSelected = item.collect
            }
            addOnClickListener(R.id.iv_collect)
        }
    }

}