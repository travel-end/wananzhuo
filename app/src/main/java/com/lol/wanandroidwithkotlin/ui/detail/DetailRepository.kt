package com.lol.wanandroidwithkotlin.ui.detail

import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.room.RoomHelper

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class DetailRepository {
    suspend fun saveReadHistory(article:Article) = RoomHelper.addReadHistory(article)
}