package com.lol.wanandroidwithkotlin.model.api

import com.lol.wanandroidwithkotlin.model.UserInfo
import com.lol.wanandroidwithkotlin.model.bean.*
import retrofit2.http.*

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/article/listproject/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int): ApiResult<Pagination<Article>>


    @POST("lg/user_article/delete/{id}/json")
    suspend fun deleteShare(@Path("id") id: Int): ApiResult<Any>

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("tree/json")
    suspend fun getArticleCategories(): ApiResult<MutableList<Category>>

    @GET("article/list/{page}/json")
    suspend fun getArticleListByCid(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>

    @GET("/user_article/list/{page}/json")
    suspend fun getUserArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("project/tree/json")
    suspend fun getProjectCategories(): ApiResult<MutableList<Category>>

    @GET("project/list/{page}/json")
    suspend fun getProjectListByCid(@Path("page") page: Int, @Query("cid") cid: Int): ApiResult<Pagination<Article>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResult<UserInfo>

    @GET("wxarticle/chapters/json")
    suspend fun getWechatCategories(): ApiResult<MutableList<Category>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWechatArticleList(
        @Path("page") page: Int,
        @Path("id") id: Int
    ): ApiResult<Pagination<Article>>


    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(
        @Field("k") keywords: String,
        @Path("page") page: Int
    ): ApiResult<Pagination<Article>>

    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    suspend fun shareArticle(
        @Field("title") title: String,
        @Field("link") link: String
    ): ApiResult<Any>

    @GET("lg/coin/userinfo/json")
    suspend fun getPoints(): ApiResult<PointRank>

    @GET("lg/coin/list/{page}/json")
    suspend fun getPointsRecord(@Path("page") page: Int): ApiResult<Pagination<PointRecord>>

    @GET("coin/rank/{page}/json")
    suspend fun getPointsRank(@Path("page") page: Int): ApiResult<Pagination<PointRank>>

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectionList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("user/lg/private_articles/{page}/json")
    suspend fun getSharedArticleList(@Path("page") page: Int): ApiResult<Shared>

    @GET("navi/json")
    suspend fun getNavigations(): ApiResult<List<Navigation>>

    @GET("banner/json")
    suspend fun getBanners(): ApiResult<List<Banner>>

    @GET("hotkey/json")
    suspend fun getHotWords(): ApiResult<List<HotWord>>

    @GET("friend/json")
    suspend fun getFrequentlyWebsites(): ApiResult<List<Frequently>>
}