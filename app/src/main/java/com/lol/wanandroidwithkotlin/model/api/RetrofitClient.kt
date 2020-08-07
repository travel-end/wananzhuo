package com.lol.wanandroidwithkotlin.model.api

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lol.wanandroidwithkotlin.MyApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Create by Jingui on 2020/6/2
 * Describe:
 * email: m15008497107@163.com
 */
object RetrofitClient {

    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(MyApp.instance)
    )
    private fun getHttpLoggerInterceptor():HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggerInterceptor())
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10,TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .addNetworkInterceptor(getHttpLoggerInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService:ApiService = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()
}