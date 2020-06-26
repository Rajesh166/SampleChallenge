package com.example.sample.repository

import com.example.sample.network.ApiServices
import com.example.sample.response.Restaurant
import com.example.sample.response.Result

open class RestaurantRepository(private val webservice: ApiServices): BaseRepository() {

    companion object {
        const val DEFAULT_LAT = 37.422740
        const val DEFAULT_LNG = -122.139956
    }

    suspend fun fetchRestaurants(latitude: Double, longitude: Double): Result<List<Restaurant>> {
        return safeApiCall("Something went wrong. Please Retry.",
                call = {
                    val response = webservice.fetchRestaurants(latitude, longitude)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Result.Success(it)
                        } ?:  Result.Error("No Data found")
                    } else {
                        Result.Error(response.errorBody().toString())
                    }
                }
        )
    }

    suspend fun fetchRestaurants(): Result<List<Restaurant>> {
        return fetchRestaurants(DEFAULT_LAT, DEFAULT_LNG)
    }

    suspend fun getRestaurantDetails(movieId: Long): Result<Restaurant> {
        return safeApiCall("Something went wrong. Please Retry.",
                call = {
                    val response = webservice.getRestaurantDetails(movieId)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Result.Success(it)
                        } ?:  Result.Error("No Data found")
                    } else {
                        Result.Error(response.errorBody().toString())
                    }
                })
    }
}