package com.lol.wanandroidwithkotlin.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lol.wanandroidwithkotlin.ext.putExtras

/**
 * Create by Jingui on 2020/6/5
 * Describe:
 * email: m15008497107@163.com
 *
 */
inline fun <reified T:Activity> Activity.ofStartActivity(params:Map<String,Any> = emptyMap()) {
    val intent = Intent(this,T::class.java)
    params.forEach {
        intent.putExtras(it.key to it.value)
    }
    startActivity(intent)
}

inline fun <reified T:Activity> Activity.ofFinish() {
    finish()
}

inline fun <reified F: Fragment> Context.newFragment(vararg  args:Pair<String,String>):F {
    val bundle = Bundle()
    args.let {
        for (arg in args) {
            bundle.putString(arg.first, arg.second)
        }
    }
    return Fragment.instantiate(this,F::class.java.name,bundle) as F
}