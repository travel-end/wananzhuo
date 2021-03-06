package com.lol.wanandroidwithkotlin.ui.register

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.ui.login.LoginActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class RegisterActivity:BaseVmActivity<RegisterViewModel>() {
    override fun viewModelClass()=RegisterViewModel::class.java
    override fun layoutRes()=R.layout.activity_register
    override fun initView() {
        ivBack.setOnClickListener { ActivityManager.finish(RegisterActivity::class.java) }
        tietConfirmPssword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                btnRegister.performClick()
                true
            } else {
                false
            }
        }
        btnRegister.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            tilConfirmPssword.error = ""

            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()
            val confirmPassword = tietConfirmPssword.text.toString()

            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                account.length < 3 -> tilAccount.error =
                    getString(R.string.account_length_over_three)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                password.length < 6 -> tilPassword.error =
                    getString(R.string.password_length_over_six)
                confirmPassword.isEmpty() -> tilConfirmPssword.error =
                    getString(R.string.confirm_password_can_not_be_empty)
                password != confirmPassword -> tilConfirmPssword.error =
                    getString(R.string.two_password_are_inconsistent)
                else -> mViewModel.register(account, password, confirmPassword)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@RegisterActivity, Observer {
                if (it) showProgressDialog(R.string.registerring) else hideProgressDialog()
            })
            registerResult.observe(this@RegisterActivity, Observer {
                if (it) {
                    ActivityManager.finish(LoginActivity::class.java, RegisterActivity::class.java)
                }
            })
        }
    }
}