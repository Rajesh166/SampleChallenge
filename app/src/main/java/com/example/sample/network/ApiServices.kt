package com.example.sample.network

import com.example.sample.response.Restaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("v2/restaurant/")
    suspend fun fetchRestaurants(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double): Response<List<Restaurant>>

    @GET("v2/restaurant/{restaurantId}")
    suspend fun getRestaurantDetails(@Path("restaurantId") movieId: Long): Response<Restaurant>
}