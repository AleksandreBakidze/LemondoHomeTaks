package com.example.homework01.services

import com.example.homework01.models.ShopData
import retrofit2.Call
import retrofit2.http.GET

interface ShopService {

    @GET("shops")
    fun getShopList () : Call<List<ShopData.Shop>>

}