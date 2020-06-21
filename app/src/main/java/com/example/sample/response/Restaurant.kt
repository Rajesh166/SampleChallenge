package com.example.sample.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable

data class Restaurant(
        val id: String,
        val name: String,
        val description: String,
        val status: String,
        @SerializedName("delivery_fee") val deliveryFee: Double,
        @SerializedName("cover_img_url") val coverImage: String
) : Serializable