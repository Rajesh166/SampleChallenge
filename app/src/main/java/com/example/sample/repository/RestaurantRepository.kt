package com.example.sample.repository

import com.example.sample.network.ApiServices
import com.example.sample.response.Restaurant
import com.example.sample.response.Result

class RestaurantRepository(private val webservice: ApiServices): BaseRepository() {
    private val DEFAULT_LAT = 37.422740
    private val DEFAULT_LNG = -122.139956

    suspend fun fetchRestaurants(): Result<List<Restaurant>> {
        return safeApiCall("Something went wrong. Please Retry.",
                call = {
                    val response = webservice.fetchRestaurants(37.422740,-122.139956)
                    if (response.isSuccessful) {
                        if (response.body() != null)
                            Result.Success(response.body()!!)
                        else {
                            Result.Error("No Data found")
                        }
                    } else {
                        Result.Error(response.errorBody().toString())
                    }
                }
        )
    }

    suspend fun getRestaurantDetails(movieId: Int): Result<Restaurant> {
        return safeApiCall("Something went wrong. Please Retry.",
                call = {
                    val response = webservice.getRestaurantDetails(movieId)
                    if (response.isSuccessful) {
                        if (response.body() != null)
                           Result.Success(response.body()!!)
                        else
                            Result.Error("No Data found")
                    } else {
                        Result.Error(response.errorBody().toString())
                    }
                })
    }
}