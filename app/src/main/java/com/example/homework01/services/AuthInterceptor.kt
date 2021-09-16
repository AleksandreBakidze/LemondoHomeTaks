package com.example.homework01.services

import com.example.homework01.helper.StoreToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainBuilder = chain.request().newBuilder()
        StoreToken.fetchToken().let {
            chainBuilder.addHeader("Authorization", "Bearer + ${StoreToken.fetchToken()}")
        }
        return chain.proceed(chainBuilder.build())
    }
}