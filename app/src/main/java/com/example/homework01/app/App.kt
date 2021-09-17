package com.example.homework01.app

import android.app.Application
import android.content.Context
import com.example.homework01.helper.StoreToken

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        StoreToken.initialize(this,getSharedPreferences("_pl_",Context.MODE_PRIVATE))
    }
}