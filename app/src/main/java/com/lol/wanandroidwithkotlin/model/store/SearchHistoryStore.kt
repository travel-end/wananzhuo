package com.lol.wanandroidwithkotlin.model.store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lol.wanandroidwithkotlin.MyApp
import com.lol.wanandroidwithkotlin.util.getSpValue
import com.lol.wanandroidwithkotlin.util.putSpValue

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
object SearchHistoryStore {
    private const val SP_SEARCH_HISTORY = "sp_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"
    private val mGson by lazy { Gson() }

    fun saveSearchHistory(words:String) {
        val history = getSearchHistory()
        if (history.contains(words)) {
            history.remove(words)
        }
        history.add(0,words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY,MyApp.instance, KEY_SEARCH_HISTORY,listStr)
    }


    fun deleteSearchHistory(words:String) {
        val history = getSearchHistory()
        history.remove(words)
        val listStr = mGson.toJson(history)
        putSpValue(SP_SEARCH_HISTORY,MyApp.instance, KEY_SEARCH_HISTORY,listStr)
    }

    fun getSearchHistory():MutableList<String> {
        val listStr= getSpValue(SP_SEARCH_HISTORY,MyApp.instance, KEY_SEARCH_HISTORY,"")
        return if (listStr.isEmpty()) {
            mutableListOf()
        } else {
            mGson.fromJson<MutableList<String>>(
                listStr,
                object :TypeToken<MutableList<String>>(){}.type
            )
        }
    }
}