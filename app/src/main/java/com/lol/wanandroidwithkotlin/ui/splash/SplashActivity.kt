package com.lol.wanandroidwithkotlin.ui.splash

import android.os.Bundle
import android.util.Log
import com.lol.wanandroidwithkotlin.MainActivity
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.ofStartActivity

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
class SplashActivity:BaseActivity() {

    override fun layoutRes() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MyApp","SplashActivity-->onCreate")
        window.decorView.postDelayed({
            ActivityManager.start(MainActivity::class.java)
            ActivityManager.finish(SplashActivity::class.java)
        },1200)

//        window.decorView.postDelayed({
//            ofStartActivity<MainActivity>()
//            ofFinish<SplashActivity>()
//        },1200)
    }
}