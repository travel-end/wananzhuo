package com.lol.wanandroidwithkotlin.ui.share

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.ext.hideSoftInput
import com.lol.wanandroidwithkotlin.ext.showToast
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_share.*

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 */
class ShareActivity : BaseVmActivity<ShareViewModel>() {

    override fun layoutRes() = R.layout.activity_share
    override fun viewModelClass() = ShareViewModel::class.java

    override fun initView() {
        ivBack.setOnClickListener { ActivityManager.finish(ShareActivity::class.java) }
        acetlink.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tvSubmit.performClick()
                true
            } else {
                false
            }
        }
        tvSubmit.setOnClickListener {
            val title = acetTitle.text.toString().trim()
            val link = acetlink.text.toString().trim()
            if (title.isEmpty()) {
                showToast(R.string.title_toast)
                return@setOnClickListener
            }
            if (link.isEmpty()) {
                showToast(R.string.link_toast)
                return@setOnClickListener
            }
            tvSubmit.hideSoftInput()
            mViewModel.shareArticle(title, link)
        }
    }

    override fun initData() {
        mViewModel.getUserInfo()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            userInfo.observe(this@ShareActivity, Observer {
                val sharePeople = if (it.nickname.isEmpty()) it.username else it.nickname
                acetSharePeople.setText(sharePeople)
            })
            submitting.observe(this@ShareActivity, Observer {
                if (it) showProgressDialog(R.string.sharing_article) else hideProgressDialog()
            })
            shareResult.observe(this@ShareActivity, Observer {
                if (it) {
                    showToast(R.string.share_article_success)
                }
            })
        }
    }
}