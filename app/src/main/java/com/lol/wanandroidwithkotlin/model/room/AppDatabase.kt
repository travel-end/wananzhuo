package com.lol.wanandroidwithkotlin.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.bean.Tag

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
@Database(entities = [Article::class, Tag::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun readHistoryDao():ReadHistoryDao
}