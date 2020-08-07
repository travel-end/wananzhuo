package com.lol.wanandroidwithkotlin.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lol.wanandroidwithkotlin.ui.login.LoginActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.bus.Bus
import com.lol.wanandroidwithkotlin.util.bus.USER_LOGIN_STATE_CHANGED

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
abstract class BaseVmFragment<VM:BaseViewModel>:BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
        observe()
        /**
         * 因为fragment恢复后savedInstanceState不为null，
         * 重新恢复后会自动从viewModel的LiveData恢复数据，不需要重新初始化数据
         */
        if (savedInstanceState == null) {
            initData()
        }
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazeLoadData()
            lazyLoaded = true
        }
    }

    open fun initView() {

    }

    open fun observe() {
        // 登录失效，跳转登录页
        mViewModel.loginStatusInvalid.observe(viewLifecycleOwner, Observer {
            if (it) {
                Bus.post(USER_LOGIN_STATE_CHANGED,false)
                ActivityManager.start(LoginActivity::class.java)
            }
        })
    }

    open fun initData() {

    }

    open fun lazeLoadData() {

    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }
    abstract fun viewModelClass():Class<VM>


    fun checkLogin(then:(() -> Unit)? = null):Boolean {
        return if (mViewModel.loginStatus()) {
            then?.invoke()
            true
        } else {
            ActivityManager.start(LoginActivity::class.java)
            false
        }
    }
}