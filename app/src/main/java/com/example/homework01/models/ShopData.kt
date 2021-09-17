package com.example.homework01.models


import com.google.gson.annotations.SerializedName

data class ShopData(
    @SerializedName("developerMessage")
    val developerMessage: Any,
    @SerializedName("errors")
    val errors: List<Any>,
    @SerializedName("httpStatusCode")
    val httpStatusCode: Int,
    @SerializedName("shops")
    val shops: List<Shop>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("userMessage")
    val userMessage: Any
)