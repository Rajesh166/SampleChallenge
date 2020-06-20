package com.example.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sample.network.RestaurantApiClient
import com.example.sample.repository.RestaurantRepository
import com.example.sample.viewmodels.RestaurantViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel::class.java)
        viewModel.init(repo = RestaurantRepository(RestaurantApiClient.apiServices))
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        viewModel.restaurantsList?.observe(this, Observer { response ->
            Log.e("response", "response" + response)
        })
    }
}