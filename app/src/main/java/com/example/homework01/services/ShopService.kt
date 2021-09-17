package com.example.homework01.services

import com.example.homework01.helper.Constants
import com.example.homework01.models.AuthToken
import com.example.homework01.models.ShopData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ShopService {

    @GET(Constants.END_POINT)
    fun getShopList () : Call<ShopData>

    @POST(Constants.TOKEN_END_POINT)
    @FormUrlEncoded
    fun getToken (
        @Field("grant_type")
        grant_type: String,
        @Field("client_id")
        client_id: String,
        @Field("client_secret")
        client_secret: String,
        @Field("scope")
        scope: String,
    ) : Call<AuthToken>
}