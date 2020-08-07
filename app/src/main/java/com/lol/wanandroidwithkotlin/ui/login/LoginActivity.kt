package com.lol.wanandroidwithkotlin.ui.login

import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import androidx.lifecycle.Observer
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.base.BaseVmActivity
import com.lol.wanandroidwithkotlin.ui.register.RegisterActivity
import com.lol.wanandroidwithkotlin.util.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
class LoginActivity:BaseVmActivity<LoginViewModel>() {
    override fun viewModelClass()=LoginViewModel::class.java
    override fun layoutRes()=R.layout.activity_login

    override fun initView() {

        ivClose.setOnClickListener {
            ActivityManager.finish(LoginActivity::class.java)
        }
        tvGoRegister.setOnClickListener {
            ActivityManager.start(RegisterActivity::class.java)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        }
        tietPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_GO) {
                btnLogin.performClick()
                true
            } else {
                false
            }
        }
        btnLogin.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }
    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.logging_in) else hideProgressDialog()
            })
            loginResult.observe(this@LoginActivity, Observer {
                if (it) {
                    ActivityManager.finish(LoginActivity::class.java)
                }
            })
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 10) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val bitmap = BitmapFactory.decodeResource(resources,R.drawable.demo)
//                val bmp = Test.compressBitmap(bitmap,200,280)
//                Test.saveFile(bmp)
//            }
//        }
//    }
}