package com.lol.wanandroidwithkotlin.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Tag

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
data class ReadHistory(
    @Embedded
    var article: Article,
    @Relation(parentColumn = "id", entityColumn = "article_id")
    var tags:List<Tag>
)