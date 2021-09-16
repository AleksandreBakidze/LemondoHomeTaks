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
            chainBuilder.addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IkZmVlF4dkZYcHpJMV9CX09rZjNuMFEiLCJ0eXAiOiJKV1QifQ.eyJuYmYiOjE2MzE4MDA2MDksImV4cCI6MTk0NzE2MDYwOSwiaXNzIjoiaHR0cHM6Ly9tb2l0YW5lLWFwaS5sZW1vbi5kby8iLCJhdWQiOlsiaHR0cHM6Ly9tb2l0YW5lLWFwaS5sZW1vbi5kby9yZXNvdXJjZXMiLCJBcGkiXSwiY2xpZW50X2lkIjoiTW9pdGFuZSIsInNjb3BlIjpbIk1vaXRhbmVBcGkiXX0.b21uxmBAPkA84MyGmvSUUMVMpessVOLjz15P0T4eQ7OFF8tgFktTYY9cF3CSYZFWLRgkqNwsj97f1gKATQ01g_VIq3aUswBNngED5L2w9B5tNGhZmyT24DLOJxMpfSfKteY2E8T_SwLsxu2Wz9itXLxsfHvYcD3OlZFI4TxI9mvnz5EJAlrRiEzrrfjUS-c86dYla2RLdjVNI4h-aFlcBptDrtsxm5BmFaMH7rZOYakr0FplaNhYfCrTmE0PKwFjhiSYLbHf0NgHTT9kDdoypW-iKJ95bR2QFhrq3ZqwIFNUx9NvGnYLp05j6LW3w5nuD29G-2vJSMW1QTN4VKZVbA")
        }
        return chain.proceed(chainBuilder.build())
    }
}