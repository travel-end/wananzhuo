package com.lol.wanandroidwithkotlin.model.room

import androidx.room.Room
import com.lol.wanandroidwithkotlin.MyApp
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Tag

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
object RoomHelper {
    private val appDatabase by lazy {
        Room.databaseBuilder(MyApp.instance,AppDatabase::class.java,"database_wanandroid").build()
    }
    private val readHistoryDao by lazy { appDatabase.readHistoryDao() }

    suspend fun queryAllReadHistory() = readHistoryDao.queryAll()
        .map {
            it.article.apply {
                tags = it.tags
            }
        }.reversed()

    suspend fun addReadHistory(article: Article) {
        readHistoryDao.queryArticle(article.id)?.let {
            readHistoryDao.deleteArticle(it)
        }
        readHistoryDao.insert(article.apply { primaryKeyId = 0 })
        article.tags.forEach {
            readHistoryDao.insertArticleTag(
                Tag(id = 0, articleId = article.id.toLong(), name = it.name, url = it.url)
            )
        }
    }

    suspend fun deleteReadHistory(article: Article) = readHistoryDao.deleteArticle(article)
}