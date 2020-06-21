package com.example.sample.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.repository.RestaurantRepository
import com.example.sample.response.Restaurant
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {

    var restaurantsList: LiveData<List<Restaurant>>? = null

    private var restaurantRepository: RestaurantRepository? = null

    fun init(repo : RestaurantRepository?) {
        restaurantRepository = repo
        restaurantsList = restaurantRepository?.restaurantList
    }

    fun retryLoading() {
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantsList = restaurantRepository?.restaurantList
        }
    }

}