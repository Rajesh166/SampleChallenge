package com.example.sample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sample.network.ApiServices
import com.example.sample.response.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantRepository(private val webservice: ApiServices) {
    private val DEFAULT_LAT = 37.422740
    private val DEFAULT_LNG = 37.422740

    val restaurantList: LiveData<List<Restaurant>>
        get() {
            val data = MutableLiveData<List<Restaurant>>()
            webservice.fetchRestaurants(37.422740,-122.139956).enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    data.value = null
                }
            })
            return data
        }
}