package com.example.sample.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(
        var id: Long = -1,
        val name: String = "",
        val description: String = "",
        var status: String = "",
        @SerializedName("phone_number") val phoneNumber: String = "",
        @SerializedName("status_type") var statusType: String = "",
        @SerializedName("average_rating") var rating: Double = 0.0,
        @SerializedName("number_of_ratings") var numberOfRatings: Int = 0,
        @SerializedName("delivery_fee") val deliveryFee: Double = 0.0,
        @SerializedName("cover_img_url") val coverImage: String = "",
        val menus: List<Menu>? = null) : Serializable {

    val statusUpdate: String
        get() = formatStatusUpdate()

    val ratingUpdate: String
        get() = formatRating()

    private fun formatStatusUpdate(): String {
        return if (statusType.isNotEmpty() && statusType.equals("Closed", ignoreCase = true)) "Closed" else status
    }

    private fun formatRating(): String {
        if (numberOfRatings <= 0) return "$rating"
        return "$rating ($numberOfRatings Users)"
    }
}

data class Menu(
        var status: String = "",
        var subtitle: String = "",
        var name: String = "")