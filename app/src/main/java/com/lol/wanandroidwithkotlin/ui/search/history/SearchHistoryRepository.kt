package com.lol.wanandroidwithkotlin.ui.search.history

import com.lol.wanandroidwithkotlin.model.api.RetrofitClient
import com.lol.wanandroidwithkotlin.model.store.SearchHistoryStore

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SearchHistoryRepository {
    suspend fun getHotSearch() = RetrofitClient.apiService.getHotWords().apiData()

    fun saveSearchHistory(searchWords: String) {
        SearchHistoryStore.saveSearchHistory(searchWords)
    }

    fun deleteSearchHistory(searchWords: String) {
        SearchHistoryStore.deleteSearchHistory(searchWords)
    }

    fun getSearchHisory() = SearchHistoryStore.getSearchHistory()
}