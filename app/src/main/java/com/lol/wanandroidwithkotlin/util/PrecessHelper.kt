package com.lol.wanandroidwithkotlin.util

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */

/**
 * 是否是主进程
 */
fun isMainProcess(context: Context)
 = context.packageName== currentPrecessName(context)

/**
 * 当前进程名称
 */
private fun currentPrecessName(context: Context):String {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (process in manager.runningAppProcesses) {
        if (process.pid == Process.myPid()) {
            return process.processName
        }
    }
    return  ""

}