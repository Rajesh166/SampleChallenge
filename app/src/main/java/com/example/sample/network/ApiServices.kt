package com.example.sample.network

import com.example.sample.response.Restaurant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET(".")
    fun fetchRestaurants(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double): Call<List<Restaurant>>
}