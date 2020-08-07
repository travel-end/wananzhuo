package com.lol.wanandroidwithkotlin.model.bean

/**
 * Create by Jingui on 2020/6/3
 * Describe:
 * email: m15008497107@163.com
 */
data class Shared(
    val coinInfo: PointRank,
    val shareArticles: Pagination<Article>
)