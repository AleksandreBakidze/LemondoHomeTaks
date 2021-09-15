package com.example.homework01.services

import com.example.homework01.models.AuthToken
import com.example.homework01.models.ShopData
import retrofit2.Call
import retrofit2.http.GET

interface ShopService {

    @GET("v1/Shops")
    fun getShopList () : Call<List<ShopData.Shop>>

    fun getToken () : Call<List<AuthToken>>

}