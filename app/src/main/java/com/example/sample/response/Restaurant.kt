package com.example.sample.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(
        val id: Long,
        val name: String,
        val description: String,
        val status: String,
        @SerializedName("phone_number") val phoneNumber: String,
        @SerializedName("status_type") val statusType: String,
        @SerializedName("average_rating") val rating: Double,
        @SerializedName("delivery_fee") val deliveryFee: Double,
        @SerializedName("cover_img_url") val coverImage: String
) : Serializable