package com.lol.wanandroidwithkotlin.util

import android.content.Context
import android.widget.ImageView
import com.lol.wanandroidwithkotlin.R
import com.lol.wanandroidwithkotlin.model.bean.Banner
import com.youth.banner.loader.ImageLoader

/**
 * Created by xiaojianjun on 2019-12-08.
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val imagePath = (path as? Banner)?.imagePath
        imageView?.loadImage(imagePath, ImageOptions().apply {
            placeholder = R.drawable.shape_bg_image_default
        })
    }

}