package com.lol.wanandroidwithkotlin.model.room

import androidx.room.*
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Tag

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
@Dao
interface ReadHistoryDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insert(article: Article):Long

    @Transaction
    @Insert(entity = Tag::class)
    suspend fun insertArticleTag(tag: Tag):Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAll() :List<ReadHistory>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticle(id:Int):Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article)

    @Transaction
    @Update(entity = Article::class)
    suspend fun updateArticle(article: Article)
}