package com.lol.wanandroidwithkotlin

import android.app.Application
import android.util.Log
import com.lol.wanandroidwithkotlin.common.ActivityLifecycleCallbacksAdapter
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.model.store.SettingsStore
import com.lol.wanandroidwithkotlin.util.ActivityManager
import com.lol.wanandroidwithkotlin.util.isMainProcess
import com.lol.wanandroidwithkotlin.util.setNightMode

/**
 * @Create by Jingui on 2020/6/2
 * @Describe:
 * @email: m15008497107@163.com
 */
class MyApp :Application(){

    companion object {
        lateinit var instance:MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


        // 主进程初始化
        if (isMainProcess(this)) {
            init()
        }
    }
    private fun init() {
        registerActivityCallbacks()
        initDayNightMode()

    }

    private fun initDayNightMode() {
        setNightMode(SettingsStore.getNightMode())
    }

    private fun registerActivityCallbacks() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = {activity, _ ->
                Log.e("MyApp","MyApp-->onActivityCreated")
                ActivityManager.activities.add(activity)

            },
            onActivityDestroyed = {activity ->
                Log.e("MyApp","MyApp-->onActivityDestroyed")
                ActivityManager.activities.remove(activity)
            }
        ))
    }
}