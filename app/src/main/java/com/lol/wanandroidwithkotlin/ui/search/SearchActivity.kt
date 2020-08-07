package com.lol.wanandroidwithkotlin.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseActivity
import com.lol.wanandroidwithkotlin.ext.hideSoftInput
import com.lol.wanandroidwithkotlin.ui.search.history.SearchHistoryFragment
import com.lol.wanandroidwithkotlin.ui.search.result.SearchResultFragment
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class SearchActivity:BaseActivity() {
    override fun layoutRes() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val historyFragment = SearchHistoryFragment.newInstance()
        val resultFragment = SearchResultFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, historyFragment)
            .add(R.id.flContainer, resultFragment)
            .show(historyFragment)
            .hide(resultFragment)
            .commit()

        ivBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else {
                ActivityManager.finish(SearchActivity::class.java)
            }
        }
        ivDone.setOnClickListener {
            val searchWords = acetInput.text.toString()
            if (searchWords.isEmpty()) return@setOnClickListener
            it.hideSoftInput()
            historyFragment.addSearchHistory(searchWords)
            resultFragment.doSearch(searchWords)
            supportFragmentManager
                .beginTransaction()
                .show(resultFragment)
                .commit()
        }
        acetInput.run {
            addTextChangedListener(afterTextChanged = {
                ivClearSearch.isGone = it.isNullOrEmpty()
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ivDone.performClick()
                    true
                } else {
                    false
                }
            }
        }
        ivClearSearch.setOnClickListener { acetInput.setText("") }
    }

    fun fillSearchInput(keywords: String) {
        acetInput.setText(keywords)
        acetInput.setSelection(keywords.length)
    }

    override fun onBackPressed() {
        ivBack.performClick()
    }
}