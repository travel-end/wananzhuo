package com.lol.wanandroidwithkotlin.ui.mime

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmFragment
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.ui.collection.CollectionActivity
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.lol.wanandroidwithkotlin.ui.history.HistoryActivity
import com.lol.wanandroidwithkotlin.ui.opensource.OpenSourceActivity
import com.lol.wanandroidwithkotlin.ui.point.MinePointsActivity
import com.lol.wanandroidwithkotlin.ui.point.rank.PointsRankActivity
import com.lol.wanandroidwithkotlin.ui.settings.SettingsActivity
import com.lol.wanandroidwithkotlin.ui.shared.SharedActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_mime.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class MineFragment:BaseVmFragment<MineViewModel>() {
    companion object {
        fun newInstance() = MineFragment()
    }
    override fun viewModelClass()=MineViewModel::class.java
    override fun layoutRes()=R.layout.fragment_mime
    override fun initView() {

        clHeader.setOnClickListener {
            checkLogin { /*上传头像，暂不支持*/ }
        }
        llMyPoints.setOnClickListener {
            checkLogin {
                ActivityManager.start(MinePointsActivity::class.java)
            }
        }
        llPointsRank.setOnClickListener {
            ActivityManager.start(PointsRankActivity::class.java)
        }
        llMyShare.setOnClickListener {
            checkLogin { ActivityManager.start(SharedActivity::class.java) }
        }
        llMyCollect.setOnClickListener {
            checkLogin { ActivityManager.start(CollectionActivity::class.java) }
        }
        llHistory.setOnClickListener {
            ActivityManager.start(HistoryActivity::class.java)
        }
        llAboutAuthor.setOnClickListener {
            ActivityManager.start(
                DetailActivity::class.java,
                mapOf(
                    PARAM_ARTICLE to Article(
                        title = getString(R.string.my_about_author),
                        link = "https://github.com/xiaoyanger0825"
                    )
                )
            )
        }
        llOpenSource.setOnClickListener {
            ActivityManager.start(OpenSourceActivity::class.java)
        }
        llSetting.setOnClickListener {
            ActivityManager.start(SettingsActivity::class.java)
        }
    }
    @SuppressLint("SetTextI18n")
    override fun observe() {
        super.observe()
        mViewModel.run {
            isLogin.observe(viewLifecycleOwner, Observer {
                tvLoginRegister.isGone = it
                tvNickName.isVisible = it
                tvId.isVisible = it
                iv_to_login.isVisible = it
            })
            userInfo.observe(viewLifecycleOwner, Observer {
                it?.let {
                    tvNickName.text = it.nickname
                    tvId.text = "ID: ${it.id}"
                }
            })
        }
        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, viewLifecycleOwner) {
            mViewModel.getUserInfo()
        }
    }

    override fun initData() {
        mViewModel.getUserInfo()
    }


}