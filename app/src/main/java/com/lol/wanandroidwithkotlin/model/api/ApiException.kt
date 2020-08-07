package com.lol.wanandroidwithkotlin.model.api

class ApiException(var code: Int, override var message: String) : RuntimeException()