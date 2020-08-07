package com.lol.wanandroidwithkotlin.ui.history

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.ui.adapter.ArticleAdapter
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_COLLECT_UPDATED
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.activity_history.*

/**
 * Created by xiaojianjun on 2019-11-29.
 */
class HistoryActivity : BaseVmActivity<HistoryViewModel>() {

    companion object {
        fun newInstance(): HistoryActivity {
            return HistoryActivity()
        }
    }

    private lateinit var mAdapter: ArticleAdapter

    override fun layoutRes() = R.layout.activity_history

    override fun viewModelClass() = HistoryViewModel::class.java

    override fun initView() {
        mAdapter = ArticleAdapter().apply {
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }
            }
            setOnItemLongClickListener { _, _, position ->
                AlertDialog.Builder(this@HistoryActivity)
                    .setMessage(R.string.confirm_delete_history)
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.confirm) { _, _ ->
                        mViewModel.deleteHistory(data[position])
                        mAdapter.remove(position)
                        this@HistoryActivity.emptyView.isVisible = data.isEmpty()
                    }.show()
                true
            }
        }
        ivBack.setOnClickListener { ActivityManager.finish(HistoryActivity::class.java) }
    }

//    override fun initData() {
//        mViewModel.getData()
//    }

    override fun onResume() {
        super.onResume()
        mViewModel.getData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            articleList.observe(this@HistoryActivity, Observer {
                mAdapter.setNewData(it)
            })
            emptyStatus.observe(this@HistoryActivity, Observer {
                emptyView.isVisible = it
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, this) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, this) {
            mViewModel.updateItemCollectState(it)
        }
    }
}