package com.example.sample.viewmodels

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.sample.response.Restaurant

class RestaurantItemViewModel(
        val data: Restaurant, private val viewModel: RestaurantListViewModel) : BaseObservable() {

    val onRestaurantClickListener: View.OnClickListener
        @Bindable
        get() = View.OnClickListener {
            viewModel.onRestaurantItemClick(restaurant = data)
        }
}