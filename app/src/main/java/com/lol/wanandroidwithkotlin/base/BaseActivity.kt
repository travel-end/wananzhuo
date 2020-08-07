package com.lol.wanandroidwithkotlin.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.lol.wanandroidwithkotlin.common.ProgressDialogFragment

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
abstract class BaseActivity:AppCompatActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    open fun layoutRes() = 0

    fun showProgressDialog(@StringRes message:Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        progressDialogFragment.show(supportFragmentManager,message,false)
    }

    fun hideProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismiss()
        }
    }

}