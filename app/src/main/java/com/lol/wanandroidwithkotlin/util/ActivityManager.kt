package com.lol.wanandroidwithkotlin.util

import android.app.Activity
import android.content.Intent
import com.lol.wanandroidwithkotlin.ext.putExtras

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
object ActivityManager {
    val activities = mutableListOf<Activity>()

    /**
     * out关键字：表示泛型参数支持协变，即接收当前类型或者他的子类
     */
    fun start(clazz: Class<out Activity>,params:Map<String,Any> = emptyMap()) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity,clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activity->
            if (clazz.contains(activity::class.java)) {
                activity.finish()
            }
        }
    }

}