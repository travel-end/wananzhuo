package com.lol.wanandroidwithkotlin.model.store

import com.lol.wanandroidwithkotlin.MyApp
import com.lol.wanandroidwithkotlin.util.getSpValue
import com.lol.wanandroidwithkotlin.util.putSpValue

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
object SettingsStore {

    private const val SP_SETTINGS = "sp_settings"
    private const val DEFAULT_WEB_TEXT_ZOOM = 100
    private const val KEY_WEB_TEXT_ZOOM = "key_web_text_zoom"
    private const val KEY_NIGHT_MODE = "key_night_mode"
    fun setWebTextZoom(textZoom:Int) {
        putSpValue(SP_SETTINGS,MyApp.instance, KEY_WEB_TEXT_ZOOM,textZoom)
    }

    fun getWebTextZoom() =
        getSpValue(SP_SETTINGS, MyApp.instance, KEY_WEB_TEXT_ZOOM, DEFAULT_WEB_TEXT_ZOOM)

    fun setNightMode(nightMode: Boolean) =
        putSpValue(SP_SETTINGS, MyApp.instance, KEY_NIGHT_MODE, nightMode)

    fun getNightMode() =
        getSpValue(SP_SETTINGS, MyApp.instance, KEY_NIGHT_MODE, false)
}