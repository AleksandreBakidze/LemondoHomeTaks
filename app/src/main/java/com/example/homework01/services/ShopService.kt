package com.example.homework01.services

import android.accounts.AccountManager
import android.os.Bundle
import com.example.homework01.models.ShopData
import retrofit2.Call
import retrofit2.http.GET

interface ShopService {

    @GET("v1/Shops")
    fun getShopList () : Call<List<ShopData.Shop>>

}