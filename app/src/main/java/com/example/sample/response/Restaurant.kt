package com.example.sample.response

import com.google.gson.annotations.SerializedName

data class Restaurant(
        val id: String,
        val name: String,
        val description: String,
        val status: String,
        @SerializedName("delivery_fee") val deliveryFee: Double
)