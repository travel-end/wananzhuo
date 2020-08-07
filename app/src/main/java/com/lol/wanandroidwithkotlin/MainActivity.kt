package com.lol.wanandroidwithkotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.lol.wanandroidwithkotlin.base.BaseActivity
import com.lol.wanandroidwithkotlin.ext.loge
import com.lol.wanandroidwithkotlin.ext.showToast
import com.lol.wanandroidwithkotlin.ui.discovery.DiscoveryFragment
import com.lol.wanandroidwithkotlin.ui.fragment.HomeFragment
import com.lol.wanandroidwithkotlin.ui.mime.MineFragment
import com.lol.wanandroidwithkotlin.ui.navigation.NavigationFragment
import com.lol.wanandroidwithkotlin.ui.system.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : BaseActivity() {

    private lateinit var fragments:Map<Int,Fragment>
    private var bottomNavigationViewAnimtor:ViewPropertyAnimator?=null
    private var currentBottomNavigationState = true
    private var previousTimeMillis = 0L
    override fun layoutRes(): Int=R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.discovery to createFragment(DiscoveryFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )

        bottomNavigationView.run {
            setOnNavigationItemSelectedListener {menuItem->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener {menuItem->
                val fragment = fragments.entries.find {
                    it.key == menuItem.itemId
                }?.value
            }
        }
        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            bottomNavigationView.selectedItemId = initialItemId
            showFragment(initialItemId)
        }

    }

    private fun createFragment(clazz: Class<out Fragment>):Fragment {
        var fragment = supportFragmentManager.fragments.find {
            it.javaClass == clazz
        }
        if (fragment == null) {
            fragment = when(clazz) {
                HomeFragment::class.java->HomeFragment.newInstance()
                SystemFragment::class.java -> SystemFragment.newInstance()
                DiscoveryFragment::class.java -> DiscoveryFragment.newInstance()
                NavigationFragment::class.java -> NavigationFragment.newInstance()
                MineFragment::class.java -> MineFragment.newInstance()
                else->throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }

        }
        return fragment
    }

    private fun showFragment(menuItemId:Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments.entries.find {
            it.key == menuItemId
        }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let {
                if (it.isVisible) hide(it)
            }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.fl, it)
            }
        }.commit()
    }

    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavigationState == show) {
            return
        }
        if (bottomNavigationViewAnimtor != null) {
            bottomNavigationViewAnimtor?.cancel()
            bottomNavigationView.clearAnimation()
        }
        currentBottomNavigationState = show
        val targetY = if (show) 0F else bottomNavigationView.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimtor = bottomNavigationView.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimtor = null
                }
            })
    }
    override fun onBackPressed() {
        val currentTimMillis = System.currentTimeMillis()
        if (currentTimMillis - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else {
            // 相当于this.showToast(...)
            showToast(R.string.press_again_to_exit)
            previousTimeMillis = currentTimMillis
        }
    }
    override fun onDestroy() {
        bottomNavigationViewAnimtor?.cancel()
        bottomNavigationView.clearAnimation()
        bottomNavigationViewAnimtor = null
        super.onDestroy()
    }
}
