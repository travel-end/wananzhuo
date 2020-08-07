package com.lol.wanandroidwithkotlin.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lol.wanandroidwithkotlin.ui.login.LoginActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
abstract class BaseVmActivity<VM:BaseViewModel>:BaseActivity() {
    protected open lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
        observe()
        if (savedInstanceState == null) {
            initData()
        }
    }
    open fun initView() {

    }

    open fun initData() {

    }
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }
    open fun observe() {
        // 登录失效  跳转登录页
        mViewModel.loginStatusInvalid.observe(this, Observer {
            if (it) {
                Bus.post(USER_LOGIN_STATE_CHANGED,false)
                ActivityManager.start(LoginActivity::class.java)
            }
        })
    }

    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (mViewModel.loginStatus()) {
            then?.invoke()
            true
        } else {
            ActivityManager.start(LoginActivity::class.java)
            false
        }
    }


    protected abstract fun viewModelClass():Class<VM>
}