package com.lol.wanandroidwithkotlin.ui.search.history

import androidx.lifecycle.MutableLiveData
import com.lol.wanandroidwithkotlin.base.BaseViewModel
import com.lol.wanandroidwithkotlin.model.bean.HotWord

class SearchHistoryViewModel : BaseViewModel() {

    private val searchHistoryRepository by lazy { SearchHistoryRepository() }

    val hotWords = MutableLiveData<List<HotWord>>()
    val searchHistory = MutableLiveData<MutableList<String>>()

    fun getHotSearch() {
        launch(block = { hotWords.value = searchHistoryRepository.getHotSearch() })
    }

    fun getSearchHistory() {
        searchHistory.value = searchHistoryRepository.getSearchHisory()
    }

    fun addSearchHistory(searchWords: String) {
        val history = searchHistory.value ?: mutableListOf()
        if (history.contains(searchWords)) {
            history.remove(searchWords)
        }
        history.add(0, searchWords)
        searchHistory.value = history
        searchHistoryRepository.saveSearchHistory(searchWords)
    }

    fun deleteSearchHistory(searchWords: String) {
        val history = searchHistory.value ?: mutableListOf()
        if (history.contains(searchWords)) {
            history.remove(searchWords)
            searchHistory.value = history
            searchHistoryRepository.deleteSearchHistory(searchWords)
        }
    }
}
