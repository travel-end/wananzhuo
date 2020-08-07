package com.lol.wanandroidwithkotlin.ui.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.BuildConfig
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.common.SeekBarChangeListenerAdapter
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.ext.setNavigationBarColor
import com.lol.wanandroidwithkotlin.ext.showToast
import com.lol.wanandroidwithkotlin.model.bean.Article
import com.lol.wanandroidwithkotlin.model.store.SettingsStore
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity
import com.lol.wanandroidwithkotlin.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.lol.wanandroidwithkotlin.ui.login.LoginActivity
import com.lol.wanandroidwithkotlin.util.*
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
@SuppressLint("SetTextI18n")
class SettingsActivity:BaseVmActivity<SettingsViewModel>() {
    override fun viewModelClass()=SettingsViewModel::class.java
    override fun layoutRes()=R.layout.activity_settings

    override fun initView() {

        setNavigationBarColor(getColor(R.color.bgColorSecondary))

        val isNightMode = isNightMode(this)
        loge("isNightMode=$isNightMode")
        scDayNight.isChecked = isNightMode
        tvFontSize.text = "${SettingsStore.getWebTextZoom()}%"
        tvClearCache.text = getCacheSize(this)
        tvAboutUs.text = getString(R.string.current_version, BuildConfig.VERSION_NAME)

        ivBack.setOnClickListener { ActivityManager.finish(SettingsActivity::class.java) }
        scDayNight.setOnCheckedChangeListener { _, checked ->
            setNightMode(checked)
            SettingsStore.setNightMode(checked)
        }
        llFontSize.setOnClickListener {
            setFontSize()
        }
        llClearCache.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_clear_cache)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    clearCache(this)
                    tvClearCache.text = getCacheSize(this)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
        llCheckVersion.setOnClickListener {
            // TODO 检查版本
            showToast(getString(R.string.stay_tuned))
        }
        llAboutUs.setOnClickListener {
            ActivityManager.start(
                DetailActivity::class.java,
                mapOf(
                    PARAM_ARTICLE to Article(
                        title = getString(R.string.abount_us),
                        link = "https://github.com/xiaoyanger0825/wanandroid"
                    )
                )
            )
        }
        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    mViewModel.logout()
                    ActivityManager.start(LoginActivity::class.java)
                    ActivityManager.finish(SettingsActivity::class.java)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
        }
    }

    private fun setFontSize() {
        val textZoom = SettingsStore.getWebTextZoom()
        var tempTextZoom = textZoom
        val view = LayoutInflater.from(this).inflate(R.layout.view_change_text_zoom, null)
        val seekBar = view.findViewById<AppCompatSeekBar>(R.id.compat_seekBar)
        AlertDialog.Builder(this)
            .setTitle(R.string.font_size)
            .setView(view)
            .setNegativeButton(R.string.cancel) { _, _ ->
                tvFontSize.text = "$textZoom%"
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                SettingsStore.setWebTextZoom(tempTextZoom)
            }
            .show()

        seekBar.progress = textZoom - 50
        seekBar.setOnSeekBarChangeListener(SeekBarChangeListenerAdapter(
            onProgressChanged = { _, progress, _ ->
                tempTextZoom = 50 + progress
                tvFontSize.text = "$tempTextZoom%"
            }
        ))
    }

    override fun initData() {
        mViewModel.getLoginStatus()
    }

    override fun observe() {
        super.observe()
        mViewModel.mIsLogin.observe(this, Observer {
            tvLogout.isVisible = it
        })
    }
}