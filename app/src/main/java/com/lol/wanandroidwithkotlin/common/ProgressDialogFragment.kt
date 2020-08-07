package com.lol.wanandroidwithkotlin.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lol.wanandroidwithkotlin.R
import kotlinx.android.synthetic.main.fragment_progress_dialog.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class ProgressDialogFragment:DialogFragment() {

    private var messageResId:Int?=null
    companion object {
        fun newInstance() = ProgressDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMessage.text = getString(messageResId?:R.string.loading)
    }

    fun show(fragmentManager: FragmentManager,
             @StringRes messageResId:Int,
             isCancelable:Boolean = false) {
        this.messageResId = messageResId
        this.isCancelable = isCancelable
        show(fragmentManager,"progressDialogFragment")
    }
}