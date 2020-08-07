package com.lol.wanandroidwithkotlin.ui.history

import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.room.RoomHelper

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class HistoryRepository {
    suspend fun getReadHistory() = RoomHelper.queryAllReadHistory()
    suspend fun deleteHistory(article: Article) = RoomHelper.deleteReadHistory(article)
}