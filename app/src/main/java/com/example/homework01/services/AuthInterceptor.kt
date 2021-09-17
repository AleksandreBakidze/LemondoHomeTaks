package com.example.homework01.services

import android.util.Log
import com.example.homework01.helper.StoreToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainBuilder = chain.request().newBuilder()
        Log.e("toke", "${StoreToken.fetchToken()}")
        StoreToken.fetchToken().let {
            chainBuilder.addHeader("Authorization", "Bearer ${StoreToken.fetchToken()}")
        }
        return chain.proceed(chainBuilder.build())
    }
}